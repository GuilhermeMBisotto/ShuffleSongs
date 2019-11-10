package com.guilhermembisotto.shuffle.ui.main

import android.os.Build
import androidx.appcompat.widget.AppCompatTextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import com.guilhermembisotto.data.songs.models.Song
import com.guilhermembisotto.shuffle.R
import com.guilhermembisotto.shuffle.ui.main.adapter.SongsAdapter
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var mainActivity: MainActivity
    private lateinit var activityController: ActivityController<MainActivity>
    private val songs = arrayListOf(
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

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(MainActivity::class.java)
        mainActivity = activityController.get()
        activityController.create().start().visible()
    }

    @Test
    fun `load songs, when set data in adapter, then show songs list`() {
        val recycler = mainActivity.binding.mainRecyclerViewSongs

        val adapter = SongsAdapter()
        recycler.adapter = adapter
        adapter.setData(songs)

        recycler.update()

        val trackName1 = recycler.findViewHolderForAdapterPosition(0)
            ?.itemView?.findViewById<AppCompatTextView>(R.id.tvItemSong_songName)?.text
        val trackName2 = recycler.findViewHolderForAdapterPosition(1)
            ?.itemView?.findViewById<AppCompatTextView>(R.id.tvItemSong_songName)?.text
        val trackName3 = recycler.findViewHolderForAdapterPosition(2)
            ?.itemView?.findViewById<AppCompatTextView>(R.id.tvItemSong_songName)?.text
        val trackName4 = recycler.findViewHolderForAdapterPosition(3)
            ?.itemView?.findViewById<AppCompatTextView>(R.id.tvItemSong_songName)?.text

        Assert.assertEquals(songs[0].trackName, trackName1)
        Assert.assertEquals(songs[1].trackName, trackName2)
        Assert.assertEquals(songs[2].trackName, trackName3)
        Assert.assertEquals(songs[3].trackName, trackName4)
    }

    @Test
    fun `get item count, when request item count, then return number of items`() {
        val adapter = SongsAdapter()

        val initialExpected = 0
        val initialActual = adapter.itemCount
        Assert.assertEquals(initialExpected, initialActual)

        adapter.setData(songs)
        val finalExpected = 4
        val finalActual = adapter.itemCount
        Assert.assertEquals(finalExpected, finalActual)
    }

    private fun RecyclerView.update() {
        this.measure(0, 0)
        this.layout(0, 0, 100, 1000)
    }
}