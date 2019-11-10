package com.guilhermembisotto.data.songs.remote.service

import com.guilhermembisotto.data.songs.models.Songs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongsServiceApi {

    @GET("lookup")
    suspend fun getSongsByArtistId(@Query("id") id: String): Response<Songs>
}