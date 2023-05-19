package com.avv2050soft.humblrrr.data.api

import com.avv2050soft.humblrrr.domain.models.response.Response
import com.avv2050soft.humblrrr.domain.models.userprofile.UserProfile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {

    @GET("/subreddits/new")
    suspend fun getNewSubreddits(
        @Header("Authorization") token: String,
        @Query("after") afterKey: String
    ) : Response

    @GET("/subreddits/popular")
    suspend fun getPopularSubreddits(
        @Header("Authorization") token: String,
        @Query("after") afterKey: String
    ) : Response

    @GET("/subreddits/search")
    suspend fun searchSubreddits(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("after") afterKey: String
    ): Response

    @GET("/r/{subredditName}")
    suspend fun loadSubredditPosts(
        @Header("Authorization") token: String,
        @Path("subredditName") subredditName: String,
        @Query("after") afterKey: String
    ): Response

    @GET("/comments/{postId}")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Path("postId") pstId: String
    ): Response

    @POST("/api/subscribe")
    suspend fun subscribeUnsubscribe(
        @Header("Authorization") token: String,
        @Query("action") action: String,
        @Query("sr_name") displayName: String
    ): Unit

    @GET("user/{username}/about")
    suspend fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("username") userName: String
    ): Response

    @GET("api/v1/me")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
    ): UserProfile

    companion object {
        private const val BASE_URL = "https://oauth.reddit.com"

        fun create(): RedditApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi::class.java)
        }
    }
}