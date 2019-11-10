package com.guilhermembisotto.shuffle.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.guilhermembisotto.core.base.BaseViewModel
import com.guilhermembisotto.core.utils.extensions.customShuffle
import com.guilhermembisotto.data.songs.contracts.SongsRepository
import com.guilhermembisotto.data.songs.models.Song
import kotlinx.coroutines.launch

class MainViewModel(
    private val songsRepository: SongsRepository
) : BaseViewModel() {

    companion object {
        private val artistsId = arrayListOf(
            "909253",
            "1171421960",
            "358714030",
            "1419227",
            "264111789"
        )
    }

    private val _songs = MutableLiveData<List<Song>?>()
    val songs = Transformations.map(_songs) { it }

    private val _loading = Transformations.map(songs) { !it.isNullOrEmpty() }
    val loading = Transformations.map(_loading) { it }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = Transformations.map(_errorMessage) { it }

    suspend fun getSongsFromApi() {
        try {
            val response = songsRepository.getSongsByArtistId(artistsId)
            _songs.postValue(response.content?.results?.filter { song ->
                song.wrapperType != Song.Companion.WrapperType.ARTIST.type
            })
        } catch (e: Exception) {
            _errorMessage.postValue(e.cause?.message)
        }
    }

    fun shuffleSongs() = viewModelScope.launch {
        _songs.value?.run {
            _songs.postValue(
                this.filter { song ->
                    song.wrapperType != Song.Companion.WrapperType.ARTIST.type
                }.customShuffle { it.artistName }
            )
        }
    }
}