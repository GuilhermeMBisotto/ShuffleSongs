package com.guilhermembisotto.data.songs.contracts

import com.guilhermembisotto.data.songs.models.Songs
import retrofit2.Response

interface SongsDataSource {

    interface Remote {
        suspend fun getSongsByArtistId(id: ArrayList<String>): Response<Songs>
    }
}