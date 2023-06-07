package com.avv2050soft.humblrrr.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.CommentsPagingSource
import com.avv2050soft.humblrrr.databinding.FragmentCommentsBinding
import com.avv2050soft.humblrrr.presentation.adapters.CommentsAdapter
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.humblrrr.presentation.utils.toastString
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.fragment_comments) {

    private val binding by viewBinding(FragmentCommentsBinding::bind)
    private val viewModel: CommentsViewModel by viewModels()
    private val commentsAdapter = CommentsAdapter(
        onAuthorClick = { authorName: String -> onAuthorClick(authorName) },
        onVoteClick = { dir, id, position -> onVoteClick(dir, id, position) }
    )

    private fun onAuthorClick(authorName: String) {
        // TODO: Show author info
    }

    private fun onVoteClick(dir: Int, id: String, position: Int) {
        var voteMessage = ""
        voteMessage = if (dir == 1) {
            "+1"
        } else "-1"
        toastString(buildString {
            append(resources.getString(R.string.vote))
            append(voteMessage)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = arguments?.getString(POST_ID)
        val postTitle = arguments?.getString(POST_TITLE)
        val postContentPictureUrl = arguments?.getString(POST_CONTENT_PICTURE)
        val isVideo = arguments?.getBoolean(IS_VIDEO)
        val fallbackUrl = arguments?.getString(FALLBACK_URL)
        CommentsPagingSource.postId = postId.toString()
        hideAppbarAndBottomView(requireActivity())
        binding.recyclerViewComments.adapter =
            commentsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
        binding.swipeRefresh.setOnRefreshListener { commentsAdapter.refresh() }
        commentsAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pageComments.onEach {
            commentsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            textViewPostTitle.text = postTitle
            val pictureSize = (resources.displayMetrics.widthPixels / 1.3).toInt()
            val requestOptions = RequestOptions()
                .override(pictureSize, pictureSize)
                .optionalFitCenter()
            if (!postContentPictureUrl.isNullOrEmpty()) {
                playerView.visibility = View.GONE
                imageViewPostContent.visibility = View.VISIBLE
                Glide.with(imageViewPostContent.context)
                    .load(postContentPictureUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .into(imageViewPostContent)
            }

            if (isVideo == true) {
                playerView.visibility = View.VISIBLE
                imageViewPostContent.visibility = View.GONE
                val videoUri = Uri.parse(
                    fallbackUrl?.substringBefore("?") ?: "?"
                )
                val player = ExoPlayer.Builder(playerView.context).build()
                player.playWhenReady
                player.repeatMode
                playerView.player = player
                val mediaItem = MediaItem.fromUri(videoUri)
                player.setMediaItem(mediaItem)
                player.prepare()
                player.play()
            } else {
                playerView.visibility = View.GONE
            }
        }
    }
}