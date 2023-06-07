package com.avv2050soft.humblrrr.domain.models.response

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("reddit_video")
    val redditVideo : RedditVideo,
)
