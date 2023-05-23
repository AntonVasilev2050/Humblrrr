package com.avv2050soft.humblrrr.domain.models.userinfo


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accept_chats")
    val acceptChats: Boolean,
    @SerializedName("accept_followers")
    val acceptFollowers: Boolean,
    @SerializedName("accept_pms")
    val acceptPms: Boolean,
    @SerializedName("awardee_karma")
    val awardeeKarma: Int,
    @SerializedName("awarder_karma")
    val awarderKarma: Int,
    @SerializedName("comment_karma")
    val commentKarma: Int,
    @SerializedName("created")
    val created: Int,
    @SerializedName("created_utc")
    val createdUtc: Int,
    @SerializedName("has_subscribed")
    val hasSubscribed: Boolean,
    @SerializedName("has_verified_email")
    val hasVerifiedEmail: Boolean,
    @SerializedName("hide_from_robots")
    val hideFromRobots: Boolean,
    @SerializedName("icon_img")
    val iconImg: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_blocked")
    val isBlocked: Boolean,
    @SerializedName("is_employee")
    val isEmployee: Boolean,
    @SerializedName("is_friend")
    val isFriend: Boolean,
    @SerializedName("is_gold")
    val isGold: Boolean,
    @SerializedName("is_mod")
    val isMod: Boolean,
    @SerializedName("link_karma")
    val linkKarma: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pref_show_snoovatar")
    val prefShowSnoovatar: Boolean,
    @SerializedName("snoovatar_img")
    val snoovatarImg: String,
    @SerializedName("snoovatar_size")
    val snoovatarSize: List<Int>,
    @SerializedName("subreddit")
    val subreddit: Subreddit,
    @SerializedName("total_karma")
    val totalKarma: Int,
    @SerializedName("verified")
    val verified: Boolean
)