package com.avv2050soft.humblrrr.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentUserProfileBinding
import com.avv2050soft.humblrrr.domain.models.userprofile.UserProfile
import com.avv2050soft.humblrrr.presentation.utils.launchAndCollectIn
import com.avv2050soft.humblrrr.presentation.utils.showBottomView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val binding by viewBinding(FragmentUserProfileBinding::bind)
    private val userProfileViewModel: UserProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomView(requireActivity())

        userProfileViewModel.userProfileStateFlow.launchAndCollectIn(viewLifecycleOwner){
            userProfileViewModel.getCurrentUserProfile()
            showUserProfile(it)
        }

        binding.buttonLogout.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_logoutFragment)
        }

        binding.buttonFriends.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_userFriendsFragment)
        }
    }

    private fun showUserProfile(userProfile: UserProfile?) {
        userProfile?.let {
            with(binding) {
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