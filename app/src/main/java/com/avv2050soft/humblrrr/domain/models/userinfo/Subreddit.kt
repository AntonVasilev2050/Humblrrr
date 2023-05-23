package com.avv2050soft.humblrrr.domain.models.userinfo


import com.google.gson.annotations.SerializedName

data class Subreddit(
    @SerializedName("accept_followers")
    val acceptFollowers: Boolean,
    @SerializedName("allowed_media_in_comments")
    val allowedMediaInComments: List<Any>,
    @SerializedName("banner_img")
    val bannerImg: String,
    @SerializedName("banner_size")
    val bannerSize: Any,
    @SerializedName("community_icon")
    val communityIcon: Any,
    @SerializedName("default_set")
    val defaultSet: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("disable_contributor_requests")
    val disableContributorRequests: Boolean,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("display_name_prefixed")
    val displayNamePrefixed: String,
    @SerializedName("free_form_reports")
    val freeFormReports: Boolean,
    @SerializedName("header_img")
    val headerImg: Any,
    @SerializedName("header_size")
    val headerSize: Any,
    @SerializedName("icon_color")
    val iconColor: String,
    @SerializedName("icon_img")
    val iconImg: String,
    @SerializedName("icon_size")
    val iconSize: List<Int>,
    @SerializedName("is_default_banner")
    val isDefaultBanner: Boolean,
    @SerializedName("is_default_icon")
    val isDefaultIcon: Boolean,
    @SerializedName("key_color")
    val keyColor: String,
    @SerializedName("link_flair_enabled")
    val linkFlairEnabled: Boolean,
    @SerializedName("link_flair_position")
    val linkFlairPosition: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("over_18")
    val over18: Boolean,
    @SerializedName("previous_names")
    val previousNames: List<Any>,
    @SerializedName("primary_color")
    val primaryColor: String,
    @SerializedName("public_description")
    val publicDescription: String,
    @SerializedName("quarantine")
    val quarantine: Boolean,
    @SerializedName("restrict_commenting")
    val restrictCommenting: Boolean,
    @SerializedName("restrict_posting")
    val restrictPosting: Boolean,
    @SerializedName("show_media")
    val showMedia: Boolean,
    @SerializedName("submit_link_label")
    val submitLinkLabel: String,
    @SerializedName("submit_text_label")
    val submitTextLabel: String,
    @SerializedName("subreddit_type")
    val subredditType: String,
    @SerializedName("subscribers")
    val subscribers: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user_is_banned")
    val userIsBanned: Boolean,
    @SerializedName("user_is_contributor")
    val userIsContributor: Boolean,
    @SerializedName("user_is_moderator")
    val userIsModerator: Boolean,
    @SerializedName("user_is_muted")
    val userIsMuted: Any,
    @SerializedName("user_is_subscriber")
    val userIsSubscriber: Boolean
)