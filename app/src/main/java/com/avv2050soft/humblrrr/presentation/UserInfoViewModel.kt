package com.avv2050soft.humblrrr.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.humblrrr.domain.models.userinfo.UserInfo
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    private var userInfo: UserInfo? = null
    private val _userInfoStateFlow = MutableStateFlow(userInfo)
    val userInfoStateFlow = _userInfoStateFlow

    fun getUserInfo(userName: String){
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
}