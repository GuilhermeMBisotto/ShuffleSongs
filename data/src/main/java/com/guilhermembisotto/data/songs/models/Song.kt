package com.guilhermembisotto.data.songs.models

data class Song(
    val id: Int,
    val wrapperType: String,
    val artistType: String?,
    val primaryGenreName: String,
    val artistName: String,
    val collectionName: String?,
    val trackExplicitness: String?,
    val trackCensoredName: String?,
    val collectionId: Int?,
    val trackName: String?,
    val country: String?,
    val artworkUrl: String?,
    val releaseData: String?,
    val artistId: Int?,
    val trackTimesMillis: Int?
) {

    companion object {

        enum class WrapperType(val type: String) {
            TRACK("track"), ARTIST("artist")
        }
    }
}