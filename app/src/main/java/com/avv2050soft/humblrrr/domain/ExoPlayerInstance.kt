package com.avv2050soft.humblrrr.domain

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerInstance {
    fun get(context: Context): ExoPlayer{
        return synchronized(this){ExoPlayer.Builder(context).build()}
    }
}