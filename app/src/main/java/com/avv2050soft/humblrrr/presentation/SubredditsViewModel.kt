package com.avv2050soft.humblrrr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.humblrrr.data.SubredditsPagingSource
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SubredditsViewModel @Inject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    val pageChildren: Flow<PagingData<Children>> = Pager(
        config = PagingConfig(pageSize = 25),
        pagingSourceFactory = { SubredditsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}