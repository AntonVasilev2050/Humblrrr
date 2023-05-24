package com.avv2050soft.humblrrr.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.CommonPagingSource
import com.avv2050soft.humblrrr.databinding.FragmentPostsBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.adapters.PostsAdapter
import com.avv2050soft.humblrrr.presentation.utils.hideAppbarAndBottomView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val USER_NAME = "user_name"

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val binding by viewBinding(FragmentPostsBinding::bind)
    private val viewModel: PostsViewModel by viewModels()
    private val postsAdapter = PostsAdapter(
        onClick = { children: Children -> onItemClick(children) },
        onClickShare = { url: String -> onShareClick(url) },
        onAuthorClick = { authorName: String -> onAuthorClick(authorName)},
        onVoteClick = { dir, id, position -> onVoteClick(dir, id, position)}
    )
    var voteDirection = 0

    private fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun onVoteClick(dir: Int, id: String, position: Int) {
        voteDirection = dir
        viewModel.vote(dir, id, position)
        showToast("voted $dir")
    }

    private fun onAuthorClick(authorName: String) {
        val bundle = Bundle()
        bundle.putString(USER_NAME, authorName)
        findNavController().navigate(R.id.action_postsFragment_to_userInfoFragment, bundle)
    }

    private fun onShareClick(url: String) {
        val intent = Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_TEXT, url)
            it.type = "text/plain"
        }
        try {
            requireContext().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(e.message)
        }
    }

    private fun onItemClick(children: Children) {
        showToast("${children.data.id} was clicked")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayName = arguments?.getString(DISPLAY_NAME_KEY)
        val bannerImage = arguments?.getString(BANNER_IMAGE_KEY)
        val icon = arguments?.getString(ICON_KEY)
        var userIsSubscriber = arguments?.getBoolean(IS_SUBSCRIBER_KEY)
        CommonPagingSource.subredditName = displayName.toString()
        hideAppbarAndBottomView(requireActivity())
        binding.recyclerViewPosts.adapter =
            postsAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener { postsAdapter.refresh() }

        postsAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagePosts.onEach {
            postsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        setupTopBanner(bannerImage, icon, displayName, userIsSubscriber)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.subscribeChannel.collect { result ->
                    if (result is ApiResult.Error) {
                        showToast(
                            UiText.ResourceString(R.string.something_went_wrong)
                                .asString(requireContext())
                        )
                    } else {
                        userIsSubscriber = !userIsSubscriber!!
                        setupTopBanner(bannerImage, icon, displayName, userIsSubscriber)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.voteChannel.collect{result ->
                    if (result is ApiResult.Error) {
                        showToast(
                            UiText.ResourceString(R.string.something_went_wrong)
                                .asString(requireContext())
                        )
                    } else {
                        postsAdapter.updatePostScore(result, voteDirection)
                    }
                }
            }
        }
    }

    private fun setupTopBanner(
        bannerImage: String?,
        icon: String?,
        displayName: String?,
        userIsSubscriber: Boolean?
    ) {
        with(binding) {
            Glide
                .with(imageViewSubredditImage.context)
                .load(bannerImage)
                .placeholder(R.drawable.reddit_red_background)
                .into(imageViewSubredditImage)
            Glide
                .with(imageViewAvatar.context)
                .load(icon)
                .placeholder(R.drawable.reddit_placeholder)
                .circleCrop()
                .into(imageViewAvatar)
            textViewSubredditName.text = displayName
            val drawable: Drawable?
            if (userIsSubscriber == true) {
                buttonSubscribe.text = getString(R.string.unsubscribe)
                buttonSubscribe.setBackgroundColor(R.drawable.rectangle_7)
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.subscribed_white,
                    context?.theme
                )
            } else {
                buttonSubscribe.text = getString(R.string.subscribe)
                buttonSubscribe.setBackgroundColor(R.drawable.rectangle_8)
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.subscribe_white,
                    context?.theme
                )
            }
            buttonSubscribe.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            buttonSubscribe.setOnClickListener {
                viewModel.subscribeUnsubscribe(displayName.toString(), userIsSubscriber == true)
            }
        }
    }
}