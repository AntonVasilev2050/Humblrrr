package com.avv2050soft.humblrrr.presentation.utils

fun Int.toStringWithKNotation(): String{
    val intToString = this.toString().trim()
    return if (intToString.length < 5){
        intToString
    }else{
        intToString.substring(0, intToString.lastIndex - 2) + "k"
    }
}