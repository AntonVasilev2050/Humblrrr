package com.avv2050soft.humblrrr.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.avv2050soft.humblrrr.databinding.FragmentFavoriteBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.adapters.SubredditAdapter
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel : FavoriteViewModel by viewModels()
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
    private fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

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
            showToast(e.message)
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
        findNavController().navigate(R.id.action_favoriteFragment_to_postsFragment, bundle)
        Toast.makeText(requireContext(), children.data.id, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomView(requireActivity())
        binding.recyclerViewSubreddits.adapter =
            subredditAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefreshFavorite.setOnRefreshListener { subredditAdapter.refresh() }

        subredditAdapter.loadStateFlow.onEach {
            binding.swipeRefreshFavorite.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        loadFavoriteSubreddits()
        handleToggleButtons()
        observeSubscribeResult()
    }

    private fun loadFavoriteSubreddits() {
        viewModel.pageFavoriteSubredditChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun loadFavoritePosts() {
        viewModel.pageFavoritePostChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleToggleButtons() {
        with(binding) {
            toggleButtonSubreddits.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    loadFavoriteSubreddits()
                    toggleButtonPosts.isChecked = false
                    setupButtons(
                        R.color.white,
                        R.drawable.rectangle_8,
                        R.color.black_transparent,
                        R.drawable.rectangle_1
                    )
                }
            }
            toggleButtonPosts.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    loadFavoritePosts()
                    toggleButtonSubreddits.isChecked = false
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.subscribeChannel.collect { result ->
                    if (result is ApiResult.Error) {
                        showToast(
                            UiText.ResourceString(R.string.something_went_wrong)
                                .asString(requireContext())
                        )
                    } else {
                        subredditAdapter.updateElement(result)
                    }
                }
            }
        }
    }
    private fun setupButtons(
        newColor: Int,
        newBackground: Int,
        popularColor: Int,
        popularBackground: Int
    ) {
        binding.toggleButtonSubreddits.setTextColor(
            resources.getColor(
                newColor,
                context?.theme
            )
        )
        binding.toggleButtonSubreddits.background = (ResourcesCompat.getDrawable(
            resources,
            newBackground,
            context?.theme
        ))
        binding.toggleButtonPosts.setTextColor(
            resources.getColor(
                popularColor,
                context?.theme
            )
        )
        binding.toggleButtonPosts.background = (ResourcesCompat.getDrawable(
            resources,
            popularBackground,
            context?.theme
        ))
    }
}