package com.avv2050soft.humblrrr.domain.models.response


import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("kind")
    val kind: String
)