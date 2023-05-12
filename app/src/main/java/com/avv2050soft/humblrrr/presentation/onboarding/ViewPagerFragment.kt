package com.avv2050soft.humblrrr.presentation.onboarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avv2050soft.humblrrr.R

class ViewPagerFragment : Fragment() {

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    private lateinit var viewModel: ViewPagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewPagerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}