package com.guilhermembisotto.shuffle.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.guilhermembisotto.data.RepositoryObject
import com.guilhermembisotto.data.songs.contracts.SongsRepository
import com.guilhermembisotto.data.songs.models.Song
import com.guilhermembisotto.data.songs.models.Songs
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class MainViewModelUnitTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: SongsRepository
    private lateinit var viewModel: MainViewModel
    private val ARTISTS_ID = arrayListOf(
        "909253",
        "1171421960",
        "358714030",
        "1419227",
        "264111789"
    )

    private val repositoryObject = RepositoryObject(
        content = Songs(
            resultCount = 6,
            results = arrayListOf(
                Song(
                    id = 1,
                    wrapperType = "artist",
                    artistName = "Eminem",
                    primaryGenreName = "Rap"
                ),
                Song(
                    id = 2,
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
                ),
                Song(
                    id = 3,
                    wrapperType = "track",
                    artistId = 1,
                    artistName = "Eminem",
                    artistType = "Rap",
                    artworkUrl = "",
                    collectionId = 2,
                    collectionName = "8 Mile",
                    country = "USA",
                    primaryGenreName = "Rap",
                    releaseData = "",
                    trackCensoredName = "Lose Yourself",
                    trackExplicitness = "Lose Yourself",
                    trackName = "Lose Yourself",
                    trackTimesMillis = 211324
                ),
                Song(
                    id = 4,
                    wrapperType = "artist",
                    artistName = "Panic! At The Disco",
                    primaryGenreName = "Pop Rock, Alternative Rock"
                ),
                Song(
                    id = 5,
                    wrapperType = "track",
                    artistId = 4,
                    artistName = "Panic! At The Disco",
                    artistType = "Pop Rock, Alternative Rock",
                    artworkUrl = "",
                    collectionId = 6,
                    collectionName = "Vices & Virtues",
                    country = "USA",
                    primaryGenreName = "Pop Rock, Alternative Rock",
                    releaseData = "",
                    trackCensoredName = "The Ballad of Mona Lisa",
                    trackExplicitness = "The Ballad of Mona Lisa",
                    trackName = "The Ballad of Mona Lisa",
                    trackTimesMillis = 21324
                ),
                Song(
                    id = 6,
                    wrapperType = "track",
                    artistId = 4,
                    artistName = "Panic! At The Disco",
                    artistType = "Pop Rock, Alternative Rock",
                    artworkUrl = "",
                    collectionId = 4,
                    collectionName = "High Hopes",
                    country = "USA",
                    primaryGenreName = "Pop Rock, Alternative Rock",
                    releaseData = "",
                    trackCensoredName = "High Hopes",
                    trackExplicitness = "High Hopes",
                    trackName = "High Hopes",
                    trackTimesMillis = 21324
                )
            )
        )
    )

    @Before
    fun setUp() {

        repository = mock()
        whenever(runBlocking { repository.getSongsByArtistId(ARTISTS_ID) }).thenReturn(
            repositoryObject
        )

        viewModel = MainViewModel(repository)
    }

    @Test
    fun `get songs, when requested to songs, then set in liveData and verify loading`() {
        runBlocking {
            val expectedSongs = repositoryObject.content?.results?.filter { song ->
                song.wrapperType != Song.Companion.WrapperType.ARTIST.type
            }

            viewModel.getSongsFromApi()

            Assert.assertEquals(viewModel.songs.getOrAwaitValue(), expectedSongs)
            Assert.assertEquals(viewModel.loading.getOrAwaitValue(), true)
        }
    }

    @Test
    fun `get songs, when requested to songs and some goes wrong, then set in error liveData and verify loading`() {
        runBlocking {

            val expectedStringError = "2000 - error"
            val expectedException = Exception().also {
                it.initCause(Throwable(expectedStringError))
            }

            whenever(repository.getSongsByArtistId(ARTISTS_ID)).thenThrow(expectedException)

            viewModel.getSongsFromApi()

            Assert.assertEquals(viewModel.errorMessage.getOrAwaitValue(), expectedStringError)
        }
    }

    /**
     * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
     *
     * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
     * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
     */
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}