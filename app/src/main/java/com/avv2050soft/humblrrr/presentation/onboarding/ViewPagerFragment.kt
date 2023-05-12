package com.avv2050soft.humblrrr.presentation.onboarding

/*
courtesy of: https://www.youtube.com/watch?v=COZ3j8Dwlog
*/
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentViewPagerBinding
import com.avv2050soft.humblrrr.presentation.onboarding.screens.FirstScreen
import com.avv2050soft.humblrrr.presentation.onboarding.screens.SecondScreen
import com.avv2050soft.humblrrr.presentation.onboarding.screens.ThirdScreen
import com.avv2050soft.humblrrr.presentation.utils.hideAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint

const val ONBOARDING_FINISHED_KEY = "onboarding_finished_key"
const val NUMBER_OF_ONBOARDING_SCREENS = 3

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding by viewBinding(FragmentViewPagerBinding::bind)
    private val viewModel: ViewPagerViewModel by viewModels()
    private lateinit var indicatorsContainer: LinearLayout
    private var adapter: ViewPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.getIsOnboardingFinished(ONBOARDING_FINISHED_KEY, false)) {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }
        viewModel.setFragmentManager(childFragmentManager)
        hideAppbarAndBottomView(requireActivity())
        setupIndicators()
        setCurrentIndicator(0)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
        )

        adapter = viewModel.getFragmentManager()?.let {
            ViewPagerAdapter(
                fragmentList,
                it,
                lifecycle
            )
        }

        binding.apply {
            viewPager.adapter = adapter
            textViewSkip.setOnClickListener {
                viewModel.saveIsOnboardingFinish(ONBOARDING_FINISHED_KEY, true)
                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    setCurrentIndicator(position)
                    when (position) {
                        in 0 until fragmentList.lastIndex -> {
                            textViewSkip.text = resources.getText(R.string.skip)
                        }

                        else -> textViewSkip.text = resources.getText(R.string.finish)
                    }
                }
            })
            (viewPager.getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER

            textViewSkip.setOnClickListener {
                viewModel.saveIsOnboardingFinish(ONBOARDING_FINISHED_KEY, true)
                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
        }
    }

    /*
    courtesy of: https://www.youtube.com/watch?v=5p59XpDUKhg
     */
    private fun setupIndicators() {
        val mainActivity = requireActivity()
        indicatorsContainer = mainActivity.findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(adapter?.itemCount ?: NUMBER_OF_ONBOARDING_SCREENS)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(mainActivity.applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val mainActivity = requireActivity()
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}