package com.avv2050soft.humblrrr.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentUserBinding
import com.avv2050soft.humblrrr.domain.models.userprofile.UserProfile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding by viewBinding(FragmentUserBinding::bind)
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                userViewModel.getCurrentUserProfile()
                userViewModel.userProfileStateFlow.collect{
                    showUserProfile(it)
                }
            }
        }

        binding.buttonLogout.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_logoutFragment)
        }
    }

    private fun showUserProfile(userProfile: UserProfile?) {
        userProfile?.let {
            with(binding){
                Glide
                    .with(imageViewAvatar.context)
                    .load(it.iconImg)
                    .into(imageViewAvatar)
                textViewProfileUserName.text = it.name
                textViewComments.text = it.subreddit.subscribers.toString()
                textViewKarma.text = it.totalKarma.toString()
            }
        }
    }
}