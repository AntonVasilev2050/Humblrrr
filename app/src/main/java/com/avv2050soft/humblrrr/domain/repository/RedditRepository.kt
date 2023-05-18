package com.avv2050soft.humblrrr.domain.repository

import com.avv2050soft.humblrrr.domain.models.response.Response

interface RedditRepository {
    suspend fun getNewSubreddits(afterKey: String): Response
    suspend fun getPopularSubreddits(afterKey: String): Response
    suspend fun subscribeUnsubscribe(action: String, displayName: String)
}