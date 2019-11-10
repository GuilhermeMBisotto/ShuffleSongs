package com.guilhermembisotto.data.songs.contracts

import com.guilhermembisotto.data.RepositoryObject
import com.guilhermembisotto.data.songs.models.Songs

interface SongsRepository {

    @Throws(Exception::class)
    suspend fun getSongsByArtistId(id: ArrayList<String>): RepositoryObject<Songs>
}