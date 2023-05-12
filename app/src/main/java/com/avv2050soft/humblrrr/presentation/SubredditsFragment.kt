package com.avv2050soft.humblrrr.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentSubredditsBinding
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditsFragment : Fragment(R.layout.fragment_subreddits) {

    private val binding by viewBinding(FragmentSubredditsBinding::bind)
    private val viewModel: SubredditsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomView(requireActivity())
        handleToggleButtons()

    }

    private fun handleToggleButtons() {
        with(binding) {
            toggleButtonNew.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // выполните действия для кнопки 1
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