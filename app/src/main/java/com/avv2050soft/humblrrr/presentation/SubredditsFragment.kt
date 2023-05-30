package com.avv2050soft.humblrrr.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.CommonPagingSource
import com.avv2050soft.humblrrr.databinding.FragmentSubredditsBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.adapters.SubredditAdapter
import com.avv2050soft.humblrrr.presentation.utils.launchAndCollectIn
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import com.avv2050soft.humblrrr.presentation.utils.toastString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val DISPLAY_NAME_KEY = "display_name_key"
const val BANNER_IMAGE_KEY = "banner_image_key"
const val ICON_KEY = "icon_key"
const val IS_SUBSCRIBER_KEY = "is_user_subscriber"

@AndroidEntryPoint
class SubredditsFragment : Fragment(R.layout.fragment_subreddits) {

    private val binding by viewBinding(FragmentSubredditsBinding::bind)
    private val viewModel: SubredditsViewModel by viewModels()
    private val subredditAdapter = SubredditAdapter(
        onClick = { children: Children -> onItemClick(children) },
        onClickSubscribe = { name, isSubscribed, position ->
            onSubscribeClick(
                name,
                isSubscribed,
                position
            )
        },
        onClickShare = { url: String -> onShareClick(url) }
    )

    private fun onShareClick(url: String) {
        val fullUrl = buildString {
            this
                .append("www.reddit.com")
                .append(url)
        }
        val intent = Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_TEXT, fullUrl)
            it.type = "text/plain"
        }
        try {
            requireContext().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            toastString(e.message)
        }
    }

    private fun onSubscribeClick(name: String, isSubscribed: Boolean, position: Int) {
        viewModel.subscribeUnsubscribe(name, isSubscribed, position)
    }

    private fun onItemClick(children: Children) {
        val bundle = Bundle()
        bundle.putString(DISPLAY_NAME_KEY, children.data.displayName)
        bundle.putString(BANNER_IMAGE_KEY, children.data.bannerImg)
        bundle.putString(ICON_KEY, children.data.iconImg)
        bundle.putBoolean(IS_SUBSCRIBER_KEY, children.data.userIsSubscriber)
        findNavController().navigate(R.id.action_subredditsFragment_to_postsFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomView(requireActivity())
        binding.recyclerViewSubreddits.adapter =
            subredditAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener { subredditAdapter.refresh() }

        subredditAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        loadSubredditsNew()
        handleToggleButtons()
        observeSubscribeResult()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    CommonPagingSource.searchQuery = query
                }
                binding.toggleButtonNew.visibility = View.GONE
                binding.toggleButtonPopular.visibility = View.GONE
                makeSearch()
                subredditAdapter.refresh()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    CommonPagingSource.searchQuery = newText
//                }
//                makeSearch()
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            loadSubredditsNew()
            binding.toggleButtonNew.isChecked = true
            binding.toggleButtonNew.visibility = View.VISIBLE
            binding.toggleButtonPopular.visibility = View.VISIBLE
            handleToggleButtons()
            return@setOnCloseListener false
        }
    }

    private fun makeSearch() {
        viewModel.pageSubredditSearchChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun loadSubredditsNew() {
        viewModel.pageSubredditNewChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun loadSubredditsPopular() {
        viewModel.pageSubredditPopularChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleToggleButtons() {
        with(binding) {
            toggleButtonNew.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // выполните действия для кнопки 1
                    loadSubredditsNew()
                    toggleButtonPopular.isChecked = false
                    setupButtons(
                        R.color.white,
                        R.drawable.rectangle_8,
                        R.color.black_transparent,
                        R.drawable.rectangle_1
                    )
                }
            }
            toggleButtonPopular.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // выполните действия для кнопки 2
                    loadSubredditsPopular()
                    toggleButtonNew.isChecked = false
                    setupButtons(
                        R.color.black_transparent,
                        R.drawable.rectangle_1,
                        R.color.white,
                        R.drawable.rectangle_8
                    )
                }
            }
        }
    }

    private fun observeSubscribeResult() {
        viewModel.subscribeChannel.launchAndCollectIn(viewLifecycleOwner) {
            if (it is ApiResult.Error) {
                toastString(resources.getString(R.string.something_went_wrong))
            } else {
                subredditAdapter.updateElement(it)
            }
        }
    }

    private fun setupButtons(
        newColor: Int,
        newBackground: Int,
        popularColor: Int,
        popularBackground: Int
    ) {
        binding.toggleButtonNew.setTextColor(
            resources.getColor(
                newColor,
                context?.theme
            )
        )
        binding.toggleButtonNew.background = (ResourcesCompat.getDrawable(
            resources,
            newBackground,
            context?.theme
        ))
        binding.toggleButtonPopular.setTextColor(
            resources.getColor(
                popularColor,
                context?.theme
            )
        )
        binding.toggleButtonPopular.background = (ResourcesCompat.getDrawable(
            resources,
            popularBackground,
            context?.theme
        ))
    }
}