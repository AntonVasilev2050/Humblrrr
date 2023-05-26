package com.avv2050soft.humblrrr.domain.models.friends


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("children")
    val children: List<Children>
)