package com.avv2050soft.humblrrr.data.api

import com.avv2050soft.humblrrr.domain.models.response.CommonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RedditApi {

    @GET("/subreddits/new")
    suspend fun getNewSubreddits(
        @Header("Authorization") token: String,
        @Query("after") afterKey: String
    ) : CommonResponse

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