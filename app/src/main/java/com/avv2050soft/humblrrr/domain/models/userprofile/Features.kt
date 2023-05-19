package com.avv2050soft.humblrrr.domain.models.userprofile


import com.google.gson.annotations.SerializedName

data class Features(
    @SerializedName("awards_on_streams")
    val awardsOnStreams: Boolean,
    @SerializedName("chat_group_rollout")
    val chatGroupRollout: Boolean,
    @SerializedName("chat_subreddit")
    val chatSubreddit: Boolean,
    @SerializedName("chat_user_settings")
    val chatUserSettings: Boolean,
    @SerializedName("cookie_consent_banner")
    val cookieConsentBanner: Boolean,
    @SerializedName("crowd_control_for_post")
    val crowdControlForPost: Boolean,
    @SerializedName("do_not_track")
    val doNotTrack: Boolean,
    @SerializedName("expensive_coins_package")
    val expensiveCoinsPackage: Boolean,
    @SerializedName("images_in_comments")
    val imagesInComments: Boolean,
    @SerializedName("is_email_permission_required")
    val isEmailPermissionRequired: Boolean,
    @SerializedName("mod_awards")
    val modAwards: Boolean,
    @SerializedName("mod_service_mute_reads")
    val modServiceMuteReads: Boolean,
    @SerializedName("mod_service_mute_writes")
    val modServiceMuteWrites: Boolean,
    @SerializedName("modlog_copyright_removal")
    val modlogCopyrightRemoval: Boolean,
    @SerializedName("modmail_harassment_filter")
    val modmailHarassmentFilter: Boolean,
    @SerializedName("mweb_link_tab")
    val mwebLinkTab: MwebLinkTab,
    @SerializedName("mweb_sharing_clipboard")
    val mwebSharingClipboard: MwebSharingClipboard,
    @SerializedName("mweb_xpromo_interstitial_comments_android")
    val mwebXpromoInterstitialCommentsAndroid: Boolean,
    @SerializedName("mweb_xpromo_interstitial_comments_ios")
    val mwebXpromoInterstitialCommentsIos: Boolean,
    @SerializedName("mweb_xpromo_modal_listing_click_daily_dismissible_android")
    val mwebXpromoModalListingClickDailyDismissibleAndroid: Boolean,
    @SerializedName("mweb_xpromo_modal_listing_click_daily_dismissible_ios")
    val mwebXpromoModalListingClickDailyDismissibleIos: Boolean,
    @SerializedName("mweb_xpromo_revamp_v2")
    val mwebXpromoRevampV2: MwebXpromoRevampV2,
    @SerializedName("mweb_xpromo_revamp_v3")
    val mwebXpromoRevampV3: MwebXpromoRevampV2,
    @SerializedName("noreferrer_to_noopener")
    val noreferrerToNoopener: Boolean,
    @SerializedName("premium_subscriptions_table")
    val premiumSubscriptionsTable: Boolean,
    @SerializedName("promoted_trend_blanks")
    val promotedTrendBlanks: Boolean,
    @SerializedName("resized_styles_images")
    val resizedStylesImages: Boolean,
    @SerializedName("show_amp_link")
    val showAmpLink: Boolean,
    @SerializedName("use_pref_account_deployment")
    val usePrefAccountDeployment: Boolean
)