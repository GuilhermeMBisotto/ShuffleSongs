package com.guilhermembisotto.data.songs.models

data class Songs(
    val resultCount: Int,
    val results: ArrayList<Song> = arrayListOf()
)