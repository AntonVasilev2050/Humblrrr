package com.avv2050soft.humblrrr.domain.repository

import com.avv2050soft.humblrrr.domain.models.response.CommonResponse

interface RedditRepository {
    suspend fun getNewSubreddits(afterKey :String) : CommonResponse
}