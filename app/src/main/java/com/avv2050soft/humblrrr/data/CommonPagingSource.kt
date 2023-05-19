package com.avv2050soft.humblrrr.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import javax.inject.Inject

class CommonPagingSource @Inject constructor(
    private val repository: RedditRepository,
    private val methodType: String
) : PagingSource<String, Children>() {
    override fun getRefreshKey(state: PagingState<String, Children>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Children> {
        val after = params.key ?: ""
        return kotlin.runCatching {
            when (methodType) {
                "new" -> repository.getNewSubreddits(after)
                "popular" -> repository.getPopularSubreddits(after)
                else -> throw IllegalArgumentException("Invalid method type")
            }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.data.children,
                    prevKey = null,
                    nextKey = it.data.after
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
}