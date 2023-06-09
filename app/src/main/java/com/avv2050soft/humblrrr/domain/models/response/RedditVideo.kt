package com.avv2050soft.humblrrr.domain.models.response

import com.google.gson.annotations.SerializedName

data class RedditVideo(
    @SerializedName("fallback_url")
    val fallbackUrl : String,
    @SerializedName("dash_url")
    val dashUrl : String,
    @SerializedName("hls_url")
    val hlsUrl : String,
    @SerializedName("scrubber_media_url")
    val scrubberMediaUrl : String,

)
