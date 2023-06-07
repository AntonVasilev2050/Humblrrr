package com.avv2050soft.humblrrr.domain.models.response

import com.google.gson.annotations.SerializedName

data class RedditVideo(
    @SerializedName("fallback_url")
    val fallbackUrl : String,
)
