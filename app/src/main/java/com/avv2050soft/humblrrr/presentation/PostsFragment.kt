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
import com.avv2050soft.humblrrr.data.CommonPagingSource
import com.avv2050soft.humblrrr.databinding.FragmentPostsBinding
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.adapters.PostsAdapter
import com.avv2050soft.humblrrr.presentation.utils.hideAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val binding by viewBinding(FragmentPostsBinding::bind)
    private val viewModel: PostsViewModel by viewModels()
    private val postsAdapter = PostsAdapter(
        onClick = { children: Children -> onItemClick(children)
        }
    )

    private fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun onItemClick(children: Children) {
        showToast("${children.data.id} was clicked")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayName = arguments?.getString(DISPLAY_NAME_KEY)
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
    }
}