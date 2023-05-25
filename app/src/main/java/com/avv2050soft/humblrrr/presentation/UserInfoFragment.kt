package com.avv2050soft.humblrrr.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.databinding.FragmentUserInfoBinding
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.userinfo.UserInfo
import com.avv2050soft.humblrrr.presentation.utils.hideAppbar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private val binding by viewBinding(FragmentUserInfoBinding::bind)
    private val viewModel: UserInfoViewModel by viewModels()

    private fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = arguments?.getString(USER_NAME)
        Toast.makeText(requireContext(), "$userName", Toast.LENGTH_SHORT).show()
        hideAppbar(requireActivity())

        userName?.let { viewModel.getUserInfo(it) }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userInfoStateFlow.collect { userInfo ->
                    userInfo?.let {
                        showUserInfo(userInfo)

                        binding.buttonBeFriends.setOnClickListener {
                            beFriends(userInfo)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.makeFriendsChannel.collect { result ->
                    if (result is ApiResult.Error) {
                        showToast(
                            UiText.ResourceString(R.string.something_went_wrong)
                                .asString(requireContext())
                        )
                    }
                }

                viewModel.doNotMakeFriendsChannel.collect { result ->
                    if (result is ApiResult.Error) {
                        showToast(
                            UiText.ResourceString(R.string.something_went_wrong)
                                .asString(requireContext())
                        )
                    }
                }
            }
        }
    }

    private fun beFriends(userInfo: UserInfo) {
        if (userInfo.data.isFriend) {
            userInfo.data.isFriend = false
            viewModel.doNotMakeFriends(userInfo.data.name)
            showUserInfo(userInfo)
        } else {
            userInfo.data.isFriend = true
            viewModel.makeFriends(userInfo.data.name)
            showUserInfo(userInfo)
        }
    }

    private fun showUserInfo(userInfo: UserInfo) {
        with(binding) {
            Glide.with(imageViewUserInfoAvatar.context)
                .load(userInfo.data.snoovatarImg)
                .placeholder(R.drawable.reddit_placeholder)
                .optionalCircleCrop()
                .into(imageViewUserInfoAvatar)
            textViewUserInfoName.text = userInfo.data.name
            textViewUserInfoKarma.text = userInfo.data.totalKarma.toString()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            textViewUserInfoCreated.text = dateFormat.format((userInfo.data.createdUtc * 1000L))
            val drawable: Drawable?
            if (userInfo.data.isFriend) {
                buttonBeFriends.text = getString(R.string.don_t_be_friends)
                buttonBeFriends.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.orange,
                        context?.theme
                    )
                )
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.subscribed_white,
                    context?.theme
                )
            } else {
                buttonBeFriends.text = getString(R.string.be_friends)
                buttonBeFriends.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.blue_main,
                        context?.theme
                    )
                )
                drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.subscribe_white,
                    context?.theme
                )
            }
            buttonBeFriends.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
    }
}