package com.avv2050soft.humblrrr.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.humblrrr.domain.models.response.Children
import com.avv2050soft.humblrrr.domain.models.response.Response
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import javax.inject.Inject

class CommonPagingSource <T : Response> @Inject constructor(
    private val repository: RedditRepository,
    private val method: suspend (String) -> T
) : PagingSource<String, Children>() {
    override fun getRefreshKey(state: PagingState<String, Children>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Children> {
        val after = params.key ?: ""
        return kotlin.runCatching {
            method(after)
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