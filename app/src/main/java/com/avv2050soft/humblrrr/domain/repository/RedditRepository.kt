package com.avv2050soft.humblrrr.domain.repository

import com.avv2050soft.humblrrr.domain.models.response.Response
import com.avv2050soft.humblrrr.domain.models.userprofile.UserProfile

interface RedditRepository {
    suspend fun getNewSubreddits(afterKey: String): Response
    suspend fun getPopularSubreddits(afterKey: String): Response
    suspend fun subscribeUnsubscribe(action: String, displayName: String)
    suspend fun loadSubredditPosts(subredditName: String, afterKey: String): Response
    suspend fun getUserProfile(): UserProfile
}