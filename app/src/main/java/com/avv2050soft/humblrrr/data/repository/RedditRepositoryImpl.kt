package com.avv2050soft.humblrrr.data.repository

import com.avv2050soft.humblrrr.data.api.RedditApi
import com.avv2050soft.humblrrr.data.auth.TokenStorage
import com.avv2050soft.humblrrr.domain.models.response.Response
import com.avv2050soft.humblrrr.domain.repository.RedditRepository

class RedditRepositoryImpl : RedditRepository {

    companion object{
        var accessToken = "Bearer ${TokenStorage.accessToken}"
    }

    override suspend fun getNewSubreddits(afterKey: String): Response {
        return RedditApi.create().getNewSubreddits(token = accessToken, afterKey = afterKey)
    }

    override suspend fun getPopularSubreddits(afterKey: String): Response {
        return RedditApi.create().getPopularSubreddits(token = accessToken, afterKey = afterKey)
    }
}