package com.avv2050soft.humblrrr.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.userinfo.UserInfo
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    private var userInfo: UserInfo? = null
    private val _userInfoStateFlow = MutableStateFlow(userInfo)
    val userInfoStateFlow = _userInfoStateFlow

    private val _makeFriendsChannel = Channel<ApiResult<String>>()
    val makeFriendsChannel = _makeFriendsChannel.receiveAsFlow()

    private val _doNotMakeFriendsChannel = Channel<ApiResult<String>>()
    val doNotMakeFriendsChannel = _doNotMakeFriendsChannel.receiveAsFlow()

    fun getUserInfo(userName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userInfo = repository.getUserInfo(userName)
            }.onSuccess {
                _userInfoStateFlow.value = userInfo
            }.onFailure {
                Log.d("data_test", "Error in VM: ${it.message.toString()}")
            }
        }
    }

    fun makeFriends(userName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.makeFriends(userName)
            }.onSuccess {
                _makeFriendsChannel.send(ApiResult.Success("Success"))
            }.onFailure {
                _makeFriendsChannel.send(ApiResult.Error(UiText.ResourceString(R.string.something_went_wrong)))
            }
        }
    }

    fun doNotMakeFriends(userName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.doNotMakeFriends(userName)
            }.onSuccess {
                _doNotMakeFriendsChannel.send(ApiResult.Success("Success"))
            }.onFailure {
                _doNotMakeFriendsChannel.send(ApiResult.Error(UiText.ResourceString(R.string.something_went_wrong)))
            }
        }
    }
}