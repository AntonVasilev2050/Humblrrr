package com.avv2050soft.humblrrr.data.repository

import android.content.Context
import com.avv2050soft.humblrrr.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

private const val PREFERENCES = "preferences"

class SharedPreferencesRepositoryImpl @Inject constructor(
    context: Context
) : SharedPreferencesRepository {

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
}