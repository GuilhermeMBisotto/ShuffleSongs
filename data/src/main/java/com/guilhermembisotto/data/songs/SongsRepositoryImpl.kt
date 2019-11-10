package com.guilhermembisotto.data.songs

import com.guilhermembisotto.data.RepositoryObject
import com.guilhermembisotto.data.SUCCESS_CODE
import com.guilhermembisotto.data.request
import com.guilhermembisotto.data.songs.contracts.SongsDataSource
import com.guilhermembisotto.data.songs.contracts.SongsRepository
import com.guilhermembisotto.data.songs.models.Songs

class SongsRepositoryImpl(
    private val songsRemoteDataSource: SongsDataSource.Remote
) : SongsRepository {

    override suspend fun getSongsByArtistId(id: ArrayList<String>): RepositoryObject<Songs> {
        val response = request {
            songsRemoteDataSource.getSongsByArtistId(id)
        }

        if (response.remote?.code != SUCCESS_CODE) {
            throw Exception().also {
                it.initCause(Throwable("${response.remote?.code} - ${response.remote?.message}"))
            }
        } else {
            return response
        }
    }
}