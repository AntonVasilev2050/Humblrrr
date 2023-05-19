package com.avv2050soft.humblrrr.domain.models.userprofile


import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("accept_followers")
    val acceptFollowers: Boolean,
    @SerializedName("awardee_karma")
    val awardeeKarma: Int,
    @SerializedName("awarder_karma")
    val awarderKarma: Int,
    @SerializedName("can_create_subreddit")
    val canCreateSubreddit: Boolean,
    @SerializedName("can_edit_name")
    val canEditName: Boolean,
    @SerializedName("coins")
    val coins: Int,
    @SerializedName("comment_karma")
    val commentKarma: Int,
    @SerializedName("created")
    val created: Double,
    @SerializedName("created_utc")
    val createdUtc: Double,
    @SerializedName("features")
    val features: Features,
    @SerializedName("force_password_reset")
    val forcePasswordReset: Boolean,
    @SerializedName("gold_creddits")
    val goldCreddits: Int,
    @SerializedName("gold_expiration")
    val goldExpiration: Any,
    @SerializedName("has_android_subscription")
    val hasAndroidSubscription: Boolean,
    @SerializedName("has_external_account")
    val hasExternalAccount: Boolean,
    @SerializedName("has_gold_subscription")
    val hasGoldSubscription: Boolean,
    @SerializedName("has_ios_subscription")
    val hasIosSubscription: Boolean,
    @SerializedName("has_mail")
    val hasMail: Boolean,
    @SerializedName("has_mod_mail")
    val hasModMail: Boolean,
    @SerializedName("has_paypal_subscription")
    val hasPaypalSubscription: Boolean,
    @SerializedName("has_stripe_subscription")
    val hasStripeSubscription: Boolean,
    @SerializedName("has_subscribed")
    val hasSubscribed: Boolean,
    @SerializedName("has_subscribed_to_premium")
    val hasSubscribedToPremium: Boolean,
    @SerializedName("has_verified_email")
    val hasVerifiedEmail: Boolean,
    @SerializedName("has_visited_new_profile")
    val hasVisitedNewProfile: Boolean,
    @SerializedName("hide_from_robots")
    val hideFromRobots: Boolean,
    @SerializedName("icon_img")
    val iconImg: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("in_beta")
    val inBeta: Boolean,
    @SerializedName("in_redesign_beta")
    val inRedesignBeta: Boolean,
    @SerializedName("inbox_count")
    val inboxCount: Int,
    @SerializedName("is_employee")
    val isEmployee: Boolean,
    @SerializedName("is_gold")
    val isGold: Boolean,
    @SerializedName("is_mod")
    val isMod: Boolean,
    @SerializedName("is_sponsor")
    val isSponsor: Boolean,
    @SerializedName("is_suspended")
    val isSuspended: Boolean,
    @SerializedName("link_karma")
    val linkKarma: Int,
    @SerializedName("linked_identities")
    val linkedIdentities: List<Any>,
    @SerializedName("name")
    val name: String,
    @SerializedName("new_modmail_exists")
    val newModmailExists: Any,
    @SerializedName("num_friends")
    val numFriends: Int,
    @SerializedName("oauth_client_id")
    val oauthClientId: String,
    @SerializedName("over_18")
    val over18: Boolean,
    @SerializedName("password_set")
    val passwordSet: Boolean,
    @SerializedName("pref_autoplay")
    val prefAutoplay: Boolean,
    @SerializedName("pref_clickgadget")
    val prefClickgadget: Int,
    @SerializedName("pref_geopopular")
    val prefGeopopular: String,
    @SerializedName("pref_nightmode")
    val prefNightmode: Boolean,
    @SerializedName("pref_no_profanity")
    val prefNoProfanity: Boolean,
    @SerializedName("pref_show_presence")
    val prefShowPresence: Boolean,
    @SerializedName("pref_show_snoovatar")
    val prefShowSnoovatar: Boolean,
    @SerializedName("pref_show_trending")
    val prefShowTrending: Boolean,
    @SerializedName("pref_show_twitter")
    val prefShowTwitter: Boolean,
    @SerializedName("pref_top_karma_subreddits")
    val prefTopKarmaSubreddits: Boolean,
    @SerializedName("pref_video_autoplay")
    val prefVideoAutoplay: Boolean,
    @SerializedName("seen_give_award_tooltip")
    val seenGiveAwardTooltip: Boolean,
    @SerializedName("seen_layout_switch")
    val seenLayoutSwitch: Boolean,
    @SerializedName("seen_premium_adblock_modal")
    val seenPremiumAdblockModal: Boolean,
    @SerializedName("seen_redesign_modal")
    val seenRedesignModal: Boolean,
    @SerializedName("seen_subreddit_chat_ftux")
    val seenSubredditChatFtux: Boolean,
    @SerializedName("snoovatar_img")
    val snoovatarImg: String,
    @SerializedName("snoovatar_size")
    val snoovatarSize: Any,
    @SerializedName("subreddit")
    val subreddit: Subreddit,
    @SerializedName("suspension_expiration_utc")
    val suspensionExpirationUtc: Any,
    @SerializedName("total_karma")
    val totalKarma: Int,
    @SerializedName("verified")
    val verified: Boolean
)