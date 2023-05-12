package com.avv2050soft.humblrrr.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentSubredditsBinding
import com.avv2050soft.humblrrr.presentation.utils.showAppbarAndBottomView
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditsFragment : Fragment(R.layout.fragment_subreddits) {

    private val binding by viewBinding(FragmentSubredditsBinding::bind)
    private val viewModel: SubredditsViewModel by viewModels()
//    private val toggleButtonNew = binding.toggleButtonNew
//    private val toggleButtonPopular = binding.toggleButtonPopular

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
                    toggleButtonNew.setTextColor(resources.getColor(R.color.white, null))
                    toggleButtonNew.background = (ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rectangle_8,
                        null
                    ))
                    toggleButtonPopular.setTextColor(resources.getColor(R.color.black_transparent, null))
                    toggleButtonPopular.background = (ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rectangle_1,
                        null
                    ))
                }
            }
            toggleButtonPopular.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // выполните действия для кнопки 2
                    toggleButtonNew.isChecked = false
                    toggleButtonPopular.setTextColor(resources.getColor(R.color.white, null))
                    toggleButtonPopular.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rectangle_8,
                        null
                    )
                    toggleButtonNew.setTextColor(resources.getColor(R.color.black_transparent, null))
                    toggleButtonNew.background = (ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rectangle_1,
                        null
                    ))
                }
            }
        }

    }

}