package com.avv2050soft.humblrrr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.humblrrr.R
import com.avv2050soft.humblrrr.data.CommonPagingSource
import com.avv2050soft.humblrrr.domain.models.ApiResult
import com.avv2050soft.humblrrr.domain.models.UiText
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    val pagePosts : Flow<PagingData<Children>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { CommonPagingSource(repository, "posts")}
    ).flow.cachedIn(viewModelScope)

    private val _subscribeChannel = Channel<ApiResult<String>>()
    val subscribeChannel = _subscribeChannel.receiveAsFlow()

    fun subscribeUnsubscribe(displayName: String, isUserSubscriber: Boolean) {
        viewModelScope.launch(Dispatchers.Default)  {
//            _subscribeChannel.send(ApiResult.Loading(position))
            kotlin.runCatching {
                if (isUserSubscriber) {
                    repository.subscribeUnsubscribe("unsub", displayName)
                } else {
                    repository.subscribeUnsubscribe("sub", displayName)
                }
            }.fold(
                onSuccess = { _subscribeChannel.send(ApiResult.Success("Success")) },
                onFailure = {
                    _subscribeChannel.send(
                        ApiResult.Error(UiText.ResourceString(R.string.something_went_wrong))
                    )
                }
            )
        }
    }
}