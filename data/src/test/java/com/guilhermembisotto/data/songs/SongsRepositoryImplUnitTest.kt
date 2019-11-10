package com.guilhermembisotto.data.songs

import com.guilhermembisotto.data.DataSourceResponse
import com.guilhermembisotto.data.RepositoryObject
import com.guilhermembisotto.data.songs.contracts.SongsDataSource
import com.guilhermembisotto.data.songs.contracts.SongsRepository
import com.guilhermembisotto.data.songs.models.Song
import com.guilhermembisotto.data.songs.models.Songs
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class SongsRepositoryImplUnitTest {

    private lateinit var songsRepository: SongsRepository
    private val songsRemoteDataSource: SongsDataSource.Remote = mock()

    private val ARTISTS_FAKE_ID = arrayListOf("0", "0", "0", "0", "0")
    private val ARTISTS_ID = arrayListOf("1")

    @Before
    fun setUp() {
        songsRepository = SongsRepositoryImpl(songsRemoteDataSource)
    }

    @Test
    fun `Get songs, when it is requested to obtain songs with fake ids, then return empty content`() {
        runBlocking {
            val expectedSongs = RepositoryObject(
                remote = DataSourceResponse(200, "OK"),
                content = Songs(0, arrayListOf())
            )
            val expectedResponse: Response<Songs> = Response.success(Songs(0, arrayListOf()))

            whenever(songsRemoteDataSource.getSongsByArtistId(ARTISTS_FAKE_ID)).thenReturn(
                expectedResponse
            )

            val response = songsRepository.getSongsByArtistId(ARTISTS_FAKE_ID)

            verify(songsRemoteDataSource, times(1)).getSongsByArtistId(ARTISTS_FAKE_ID)
            Assert.assertEquals(response.content, expectedSongs.content)
            Assert.assertEquals(response.local, expectedSongs.local)
            Assert.assertEquals(response.remote?.code, expectedSongs.remote?.code)
            Assert.assertEquals(response.remote?.message, expectedSongs.remote?.message)
        }
    }

    @Test
    fun `Get songs, when it is requested to obtain songs with real ids, then return a content with values`() {
        runBlocking {

            val expectedSong = Song(
                id = 1,
                wrapperType = "track",
                artistId = 1,
                artistName = "Eminem",
                artistType = "Rap",
                artworkUrl = "",
                collectionId = 1,
                collectionName = "Kamikaze",
                country = "USA",
                primaryGenreName = "Rap",
                releaseData = "",
                trackCensoredName = "Kamikaze",
                trackExplicitness = "Kamikaze",
                trackName = "Kamikaze",
                trackTimesMillis = 21324
            )

            val expectedSongs = RepositoryObject(
                remote = DataSourceResponse(200, "OK"),
                content = Songs(1, arrayListOf(expectedSong))
            )
            val expectedResponse: Response<Songs> = Response.success(
                Songs(1, arrayListOf(expectedSong))
            )

            whenever(songsRemoteDataSource.getSongsByArtistId(ARTISTS_ID)).thenReturn(
                expectedResponse
            )

            val response = songsRepository.getSongsByArtistId(ARTISTS_ID)

            verify(songsRemoteDataSource, times(1)).getSongsByArtistId(ARTISTS_ID)
            Assert.assertEquals(response.local, expectedSongs.local)

            Assert.assertEquals(response.remote?.code, expectedSongs.remote?.code)
            Assert.assertEquals(response.remote?.message, expectedSongs.remote?.message)

            Assert.assertEquals(response.content, expectedSongs.content)
            Assert.assertEquals(response.content?.resultCount, expectedSongs.content?.resultCount)
            Assert.assertEquals(response.content?.results, expectedSongs.content?.results)
            Assert.assertEquals(
                response.content?.results!![0].id,
                expectedSongs.content?.results!![0].id
            )
            Assert.assertEquals(
                response.content?.results!![0].wrapperType,
                expectedSongs.content?.results!![0].wrapperType
            )
            Assert.assertEquals(
                response.content?.results!![0].artworkUrl,
                expectedSongs.content?.results!![0].artworkUrl
            )
            Assert.assertEquals(
                response.content?.results!![0].country,
                expectedSongs.content?.results!![0].country
            )
            Assert.assertEquals(
                response.content?.results!![0].releaseData,
                expectedSongs.content?.results!![0].releaseData
            )
            Assert.assertEquals(
                response.content?.results!![0].trackTimesMillis,
                expectedSongs.content?.results!![0].trackTimesMillis
            )
            Assert.assertEquals(
                response.content?.results!![0].primaryGenreName,
                expectedSongs.content?.results!![0].primaryGenreName
            )
            Assert.assertEquals(
                response.content?.results!![0].artistId,
                expectedSongs.content?.results!![0].artistId
            )
            Assert.assertEquals(
                response.content?.results!![0].artistType,
                expectedSongs.content?.results!![0].artistType
            )
            Assert.assertEquals(
                response.content?.results!![0].artistName,
                expectedSongs.content?.results!![0].artistName
            )
            Assert.assertEquals(
                response.content?.results!![0].collectionId,
                expectedSongs.content?.results!![0].collectionId
            )
            Assert.assertEquals(
                response.content?.results!![0].collectionName,
                expectedSongs.content?.results!![0].collectionName
            )
            Assert.assertEquals(
                response.content?.results!![0].trackName,
                expectedSongs.content?.results!![0].trackName
            )
            Assert.assertEquals(
                response.content?.results!![0].trackCensoredName,
                expectedSongs.content?.results!![0].trackCensoredName
            )
            Assert.assertEquals(
                response.content?.results!![0].trackExplicitness,
                expectedSongs.content?.results!![0].trackExplicitness
            )

            Assert.assertEquals(
                response.content?.results!![0].wrapperType == Song.Companion.WrapperType.TRACK.type,
                expectedSongs.content?.results!![0].wrapperType == Song.Companion.WrapperType.TRACK.type
            )
        }
    }
}