package com.avv2050soft.humblrrr.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentSubredditsBinding
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.humblrrr.presentation.adapters.SubredditAdapter
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val CHILD_ID_KEY = "child_id_key"

@AndroidEntryPoint
class SubredditsFragment : Fragment(R.layout.fragment_subreddits) {

    private val binding by viewBinding(FragmentSubredditsBinding::bind)
    private val viewModel: SubredditsViewModel by viewModels()
    private val subredditAdapter = SubredditAdapter(
        onClick = { children: Children -> onItemClick(children) },
        onClickImage = { children: Children -> onImageClick(children) }
    )

    private fun onImageClick(children: Children) {
        Toast.makeText(
            requireContext(),
            "Image in position ${children.data.id} was clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onItemClick(children: Children) {
        val childrenBundle = Bundle()
        childrenBundle.putString(CHILD_ID_KEY, children.data.id)
//        findNavController().navigate(
//            R.id.action_,
//            childrenBundle
//        )
        Toast.makeText(requireContext(), children.data.id, Toast.LENGTH_SHORT).show()
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

        loadSubreddits()

        handleToggleButtons()

    }

    private fun loadSubreddits() {
        viewModel.pageChildren.onEach {
            subredditAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleToggleButtons() {
        with(binding) {
            toggleButtonNew.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // выполните действия для кнопки 1
                    loadSubreddits()
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