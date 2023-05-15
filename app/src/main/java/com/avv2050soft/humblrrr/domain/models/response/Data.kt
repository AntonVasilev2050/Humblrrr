package com.avv2050soft.humblrrr.domain.models.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: Any,
    @SerializedName("children")
    val children: List<Children>,
    @SerializedName("dist")
    val dist: Int,
    @SerializedName("geo_filter")
    val geoFilter: String,
    @SerializedName("modhash")
    val modhash: Any
)