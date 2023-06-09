package com.avv2050soft.humblrrr.domain

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerInstance {

//        @Volatile
//        private var INSTANCE: ExoPlayer? = null
//        fun get(context: Context): ExoPlayer {
//            return INSTANCE ?: synchronized(this){
//                val instance = ExoPlayer.Builder(context).build()
//                INSTANCE = instance
//                instance
//            }
//        }

    fun get(context: Context): ExoPlayer{
        return synchronized(this){ExoPlayer.Builder(context).build()}
    }
}