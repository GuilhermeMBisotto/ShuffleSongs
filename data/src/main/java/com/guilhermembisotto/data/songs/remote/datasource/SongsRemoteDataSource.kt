package com.guilhermembisotto.data.songs.remote.datasource

import com.guilhermembisotto.data.songs.contracts.SongsDataSource
import com.guilhermembisotto.data.songs.models.Songs
import com.guilhermembisotto.data.songs.remote.service.SongsServiceApi
import retrofit2.Response

class SongsRemoteDataSource(
    private val songsServiceApi: SongsServiceApi
) : SongsDataSource.Remote {

    companion object {
        private const val SEPARATOR = ","
    }

    override suspend fun getSongsByArtistId(id: ArrayList<String>): Response<Songs> =
        songsServiceApi.getSongsByArtistId(id.joinToString(SEPARATOR))
}