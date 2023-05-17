package com.avv2050soft.humblrrr.domain.models.response


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("kind")
    val kind: String
)