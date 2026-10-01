package com.practicum.myapplication.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    private val _playlistsState = MutableStateFlow<PlaylistsLoadState>(PlaylistsLoadState.Initial)
    val playlistsState = _playlistsState.asStateFlow()

    var selectedPlaylist: Playlist? = null
        private set
    fun setSelectedPlaylist(playlist: Playlist){
        selectedPlaylist = playlist
    }

    // Эта функция вызовется, когда пользователь откроет вкладку "Медиатека"
    fun loadPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _playlistsState.update { PlaylistsLoadState.Loading }
                val playlists = playlistsRepository.getPlaylists()
                _playlistsState.update { PlaylistsLoadState.Success(list = playlists) }
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsLoadState.Fail(error = e.message.toString()) }
            }
        }
    }

    fun createNewPlaylist(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistsRepository.createPlaylist(title, description)
                loadPlaylists()
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsLoadState.Fail(error = e.message.toString()) }
            }
        }
    }

    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistsRepository.addTrackToPlaylist(track, playlist.id)
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsLoadState.Fail(error = e.message.toString()) }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(Creator.getPlaylistRepository()) as T
                }
            }
    }
}