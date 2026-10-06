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

    private val _selectedPlaylistState = MutableStateFlow<Playlist?>(null)
    val selectedPlaylistState = _selectedPlaylistState.asStateFlow()

    fun setSelectedPlaylist(playlist: Playlist) {
        _selectedPlaylistState.value = playlist
    }

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
                playlistsRepository.addTrackToPlaylist(track, playlist)
                playlistsRepository.updatePlaylistSumTrackTimeMillisById(playlist.id)
                val updatedPlaylist = playlistsRepository.getPlaylistById(playlist.id)
                _selectedPlaylistState.update { updatedPlaylist }
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsLoadState.Fail(error = e.message.toString()) }
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistsRepository.deletePlaylist(playlist)
                loadPlaylists()
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsLoadState.Fail(error = e.message.toString()) }
            }
        }
    }

    fun removeTrackFromPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistsRepository.removeTrackFromPlaylist(track, playlist)
                playlistsRepository.updatePlaylistSumTrackTimeMillisById(playlist.id)
                val updatedPlaylist = playlistsRepository.getPlaylistById(playlist.id)
                _selectedPlaylistState.update { updatedPlaylist }
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