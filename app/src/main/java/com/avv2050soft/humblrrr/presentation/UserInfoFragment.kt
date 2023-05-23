package com.avv2050soft.humblrrr.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private val binding by viewBinding(FragmentUserInfoBinding::bind)
    private val viewModel: UserInfoViewModel by viewModels()
}