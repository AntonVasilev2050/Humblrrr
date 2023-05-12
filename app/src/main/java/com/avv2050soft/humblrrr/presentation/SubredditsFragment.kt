package com.avv2050soft.humblrrr.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avv2050soft.humblrrr.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditsFragment : Fragment() {

    companion object {
        fun newInstance() = SubredditsFragment()
    }

    private lateinit var viewModel: SubredditsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subreddits, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubredditsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}