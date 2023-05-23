package com.avv2050soft.humblrrr.domain.models.userinfo


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("kind")
    val kind: String
)