package com.guilhermembisotto.core.utils.extensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guilhermembisotto.data.songs.models.Song
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CustomShuffleByUnitTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val orignalSongs = arrayListOf(
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

    @Test
    fun `shuffle songs, when customShuffle was used, then return a different list from original`() {
        Assert.assertNotEquals(orignalSongs, orignalSongs.customShuffle { it.artistName })
    }

    @Test
    fun `shuffle songs, when shuffle 100 times, then shuffle return always a new list different from previous`() {

        var previous = orignalSongs.toList()

        for (i in 0..100) {
            val new = previous.customShuffle { it.artistName }
            Assert.assertNotEquals(previous, new)
            previous = new
        }
    }

    @Test
    fun `shuffle songs, when shuffle 100 times, then return always a list with no sequential artist`() {
        for (i in 0..100) {
            val shuffled = orignalSongs.customShuffle { it.artistName }

            for (j in 1 until shuffled.size) {
                Assert.assertNotEquals(shuffled[j].artistName, shuffled[j - 1].artistName)
            }
        }
    }
}