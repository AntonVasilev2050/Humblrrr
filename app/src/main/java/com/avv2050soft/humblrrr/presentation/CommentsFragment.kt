package com.avv2050soft.humblrrr.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.CommentsPagingSource
import com.avv2050soft.humblrrr.databinding.FragmentCommentsBinding
import com.avv2050soft.humblrrr.presentation.adapters.CommentsAdapter
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.utils.hideAppbarAndBottomView
import com.bumptech.glide.Glide
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
    private fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun onAuthorClick(authorName: String) {
        showToast("Comment of $authorName was clicked")
    }

    private fun onVoteClick(dir: Int, id: String, position: Int) {
        var voteMessage = ""
        if (dir == 1){
            voteMessage = "+1"
        }else voteMessage = "-1"
        showToast("Vote $voteMessage")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = arguments?.getString(POST_ID)
        val postTitle = arguments?.getString(POST_TITLE)
        val postContentPictureUrl = arguments?.getString(POST_CONTENT_PICTURE)
        CommentsPagingSource.postId = postId.toString()
        hideAppbarAndBottomView(requireActivity())
        binding.recyclerViewComments.adapter = commentsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
        binding.swipeRefresh.setOnRefreshListener { commentsAdapter.refresh() }
        commentsAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pageComments.onEach {
            commentsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding){
            textViewPostTitle.text = postTitle
            Glide.with(imageViewPostContent.context)
                .load(postContentPictureUrl)
                .into(imageViewPostContent)
        }
    }
}