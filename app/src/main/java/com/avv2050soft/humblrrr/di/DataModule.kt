package com.avv2050soft.humblrrr.di

import android.content.Context
import com.avv2050soft.humblrrr.data.repository.RedditRepositoryImpl
import com.avv2050soft.humblrrr.data.repository.SharedPreferencesRepositoryImpl
import com.avv2050soft.humblrrr.domain.repository.RedditRepository
import com.avv2050soft.humblrrr.domain.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideRedditRepository(): RedditRepository {
        return RedditRepositoryImpl()
    }
}