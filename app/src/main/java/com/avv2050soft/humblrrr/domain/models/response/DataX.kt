package com.avv2050soft.humblrrr.domain.models.response


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("accept_followers")
    val acceptFollowers: Boolean,
    @SerializedName("accounts_active")
    val accountsActive: Any,
    @SerializedName("accounts_active_is_fuzzed")
    val accountsActiveIsFuzzed: Boolean,
    @SerializedName("active_user_count")
    val activeUserCount: Any,
    @SerializedName("advertiser_category")
    val advertiserCategory: String,
    @SerializedName("all_original_content")
    val allOriginalContent: Boolean,
    @SerializedName("allow_chat_post_creation")
    val allowChatPostCreation: Boolean,
    @SerializedName("allow_discovery")
    val allowDiscovery: Boolean,
    @SerializedName("allow_galleries")
    val allowGalleries: Boolean,
    @SerializedName("allow_images")
    val allowImages: Boolean,
    @SerializedName("allow_polls")
    val allowPolls: Boolean,
    @SerializedName("allow_prediction_contributors")
    val allowPredictionContributors: Boolean,
    @SerializedName("allow_predictions")
    val allowPredictions: Boolean,
    @SerializedName("allow_predictions_tournament")
    val allowPredictionsTournament: Boolean,
    @SerializedName("allow_talks")
    val allowTalks: Boolean,
    @SerializedName("allow_videogifs")
    val allowVideogifs: Boolean,
    @SerializedName("allow_videos")
    val allowVideos: Boolean,
    @SerializedName("allowed_media_in_comments")
    val allowedMediaInComments: List<String>,
    @SerializedName("banner_background_color")
    val bannerBackgroundColor: String,
    @SerializedName("banner_background_image")
    val bannerBackgroundImage: String,
    @SerializedName("banner_img")
    val bannerImg: String,
    @SerializedName("banner_size")
    val bannerSize: Any,
    @SerializedName("can_assign_link_flair")
    val canAssignLinkFlair: Boolean,
    @SerializedName("can_assign_user_flair")
    val canAssignUserFlair: Boolean,
    @SerializedName("collapse_deleted_comments")
    val collapseDeletedComments: Boolean,
    @SerializedName("comment_contribution_settings")
    val commentContributionSettings: CommentContributionSettings,
    @SerializedName("comment_score_hide_mins")
    val commentScoreHideMins: Int,
    @SerializedName("community_icon")
    val communityIcon: String,
    @SerializedName("community_reviewed")
    val communityReviewed: Boolean,
    @SerializedName("created")
    val created: Int,
    @SerializedName("created_utc")
    val createdUtc: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_html")
    val descriptionHtml: Any,
    @SerializedName("disable_contributor_requests")
    val disableContributorRequests: Boolean,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("display_name_prefixed")
    val displayNamePrefixed: String,
    @SerializedName("emojis_custom_size")
    val emojisCustomSize: Any,
    @SerializedName("emojis_enabled")
    val emojisEnabled: Boolean,
    @SerializedName("free_form_reports")
    val freeFormReports: Boolean,
    @SerializedName("has_menu_widget")
    val hasMenuWidget: Boolean,
    @SerializedName("header_img")
    val headerImg: Any,
    @SerializedName("header_size")
    val headerSize: Any,
    @SerializedName("header_title")
    val headerTitle: String,
    @SerializedName("hide_ads")
    val hideAds: Boolean,
    @SerializedName("icon_img")
    val iconImg: String,
    @SerializedName("icon_size")
    val iconSize: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_chat_post_feature_enabled")
    val isChatPostFeatureEnabled: Boolean,
    @SerializedName("is_crosspostable_subreddit")
    val isCrosspostableSubreddit: Boolean,
    @SerializedName("is_enrolled_in_new_modmail")
    val isEnrolledInNewModmail: Any,
    @SerializedName("key_color")
    val keyColor: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("link_flair_enabled")
    val linkFlairEnabled: Boolean,
    @SerializedName("link_flair_position")
    val linkFlairPosition: String,
    @SerializedName("mobile_banner_image")
    val mobileBannerImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("notification_level")
    val notificationLevel: Any,
    @SerializedName("original_content_tag_enabled")
    val originalContentTagEnabled: Boolean,
    @SerializedName("over18")
    val over18: Boolean,
    @SerializedName("prediction_leaderboard_entry_type")
    val predictionLeaderboardEntryType: String,
    @SerializedName("primary_color")
    val primaryColor: String,
    @SerializedName("public_description")
    val publicDescription: String,
    @SerializedName("public_description_html")
    val publicDescriptionHtml: String,
    @SerializedName("public_traffic")
    val publicTraffic: Boolean,
    @SerializedName("quarantine")
    val quarantine: Boolean,
    @SerializedName("restrict_commenting")
    val restrictCommenting: Boolean,
    @SerializedName("restrict_posting")
    val restrictPosting: Boolean,
    @SerializedName("should_archive_posts")
    val shouldArchivePosts: Boolean,
    @SerializedName("should_show_media_in_comments_setting")
    val shouldShowMediaInCommentsSetting: Boolean,
    @SerializedName("show_media")
    val showMedia: Boolean,
    @SerializedName("show_media_preview")
    val showMediaPreview: Boolean,
    @SerializedName("spoilers_enabled")
    val spoilersEnabled: Boolean,
    @SerializedName("submission_type")
    val submissionType: String,
    @SerializedName("submit_link_label")
    val submitLinkLabel: String,
    @SerializedName("submit_text")
    val submitText: String,
    @SerializedName("submit_text_html")
    val submitTextHtml: Any,
    @SerializedName("submit_text_label")
    val submitTextLabel: String,
    @SerializedName("subreddit_type")
    val subredditType: String,
    @SerializedName("subscribers")
    val subscribers: Int,
    @SerializedName("suggested_comment_sort")
    val suggestedCommentSort: Any,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user_can_flair_in_sr")
    val userCanFlairInSr: Any,
    @SerializedName("user_flair_background_color")
    val userFlairBackgroundColor: Any,
    @SerializedName("user_flair_css_class")
    val userFlairCssClass: Any,
    @SerializedName("user_flair_enabled_in_sr")
    val userFlairEnabledInSr: Boolean,
    @SerializedName("user_flair_position")
    val userFlairPosition: String,
    @SerializedName("user_flair_richtext")
    val userFlairRichtext: List<Any>,
    @SerializedName("user_flair_template_id")
    val userFlairTemplateId: Any,
    @SerializedName("user_flair_text")
    val userFlairText: Any,
    @SerializedName("user_flair_text_color")
    val userFlairTextColor: Any,
    @SerializedName("user_flair_type")
    val userFlairType: String,
    @SerializedName("user_has_favorited")
    val userHasFavorited: Boolean,
    @SerializedName("user_is_banned")
    val userIsBanned: Boolean,
    @SerializedName("user_is_contributor")
    val userIsContributor: Boolean,
    @SerializedName("user_is_moderator")
    val userIsModerator: Boolean,
    @SerializedName("user_is_muted")
    val userIsMuted: Boolean,
    @SerializedName("user_is_subscriber")
    val userIsSubscriber: Boolean,
    @SerializedName("user_sr_flair_enabled")
    val userSrFlairEnabled: Any,
    @SerializedName("user_sr_theme_enabled")
    val userSrThemeEnabled: Boolean,
    @SerializedName("videostream_links_count")
    val videostreamLinksCount: Int,
    @SerializedName("whitelist_status")
    val whitelistStatus: Any,
    @SerializedName("wiki_enabled")
    val wikiEnabled: Any,
    @SerializedName("wls")
    val wls: Any
)