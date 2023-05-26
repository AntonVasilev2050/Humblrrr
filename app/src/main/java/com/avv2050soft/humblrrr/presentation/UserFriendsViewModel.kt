package com.avv2050soft.humblrrr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.FriendsPagingSource
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.friends.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFriendsViewModel @Inject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    val pageFriends: Flow<PagingData<Children>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { FriendsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    private val _doNotMakeFriendsChannel = Channel<ApiResult<String>>()
    val doNotMakeFriendsChannel = _doNotMakeFriendsChannel.receiveAsFlow()

    fun doNotMakeFriends(userName: String) {
        viewModelScope.launch {
            runCatching {
                repository.doNotMakeFriends(userName)
            }.onSuccess {
                _doNotMakeFriendsChannel.send(ApiResult.Success("Success"))
            }.onFailure {
                _doNotMakeFriendsChannel.send(ApiResult.Error(UiText.ResourceString(R.string.something_went_wrong)))
            }
        }
    }
}