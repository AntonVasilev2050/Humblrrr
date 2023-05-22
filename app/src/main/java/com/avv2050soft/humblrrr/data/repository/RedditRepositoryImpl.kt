package com.avv2050soft.humblrrr.data.repository

import com.avv2050soft.humblrrr.data.api.RedditApi
import com.avv2050soft.humblrrr.data.auth.TokenStorage
import com.avv2050soft.humblrrr.domain.models.response.Response
import com.avv2050soft.humblrrr.domain.models.userprofile.UserProfile
import com.avv2050soft.humblrrr.domain.repository.RedditRepository

class RedditRepositoryImpl : RedditRepository {

    companion object {
        var accessToken = "Bearer ${TokenStorage.accessToken}"
    }

    override suspend fun getNewSubreddits(afterKey: String): Response {
        return RedditApi.create().getNewSubreddits(token = accessToken, afterKey = afterKey)
    }

    override suspend fun getPopularSubreddits(afterKey: String): Response {
        return RedditApi.create().getPopularSubreddits(token = accessToken, afterKey = afterKey)
    }

    override suspend fun searchSubreddits(query: String, afterKey: String): Response {
        return RedditApi.create()
            .searchSubreddits(token = accessToken, query = query, afterKey = afterKey)
    }

    override suspend fun subscribeUnsubscribe(action: String, displayName: String) {
        RedditApi.create().subscribeUnsubscribe(token = accessToken, action, displayName)
    }

    override suspend fun loadSubredditPosts(subredditName: String, afterKey: String): Response {
        return RedditApi.create()
            .loadSubredditPosts(token = accessToken, subredditName, afterKey = afterKey)
    }

    override suspend fun loadFavoriteSubreddits(afterKey: String): Response {
        return RedditApi.create().loadFavoriteSubreddits(token = accessToken, afterKey = afterKey)
    }

    override suspend fun loadFavoritePosts(
        afterKey: String,
        userName: String,
        type: String
    ): Response {
        return RedditApi.create()
            .loadFavoritePosts(token = accessToken, userName = userName, after = afterKey)
    }

    override suspend fun getUserProfile(): UserProfile {
        return RedditApi.create().getUserProfile(token = accessToken)
    }
}