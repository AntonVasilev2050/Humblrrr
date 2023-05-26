package com.avv2050soft.humblrrr.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.humblrrr.domain.models.friends.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import javax.inject.Inject

class FriendsPagingSource @Inject constructor(
    private val repository: RedditRepository
) : PagingSource<String, Children>(){
    override fun getRefreshKey(state: PagingState<String, Children>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Children> {
        val after = params.key ?: ""
        return kotlin.runCatching {
            repository.getUserFriends()
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.data.children,
                    prevKey = null,
//                    nextKey = it.data.children[it.data.children.lastIndex].id
                    nextKey = null
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}