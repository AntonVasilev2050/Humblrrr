package com.avv2050soft.humblrrr.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import javax.inject.Inject

class CommentsPagingSource @Inject constructor(
    private val repository: RedditRepository
) : PagingSource<String, Children>(){
    override fun getRefreshKey(state: PagingState<String, Children>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Children> {
        val after = params.key ?: ""
        return kotlin.runCatching {
            repository.getComments(postId = postId)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it[1].data.children,
                    prevKey = null,
                    nextKey = it[1].data.after
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    companion object{
        var postId = ""
    }
}