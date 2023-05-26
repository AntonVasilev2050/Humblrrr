package com.avv2050soft.humblrrr.domain.models.friends


import com.google.gson.annotations.SerializedName

data class UserFriends(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("kind")
    val kind: String
)