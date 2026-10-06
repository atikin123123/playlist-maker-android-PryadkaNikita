package com.practicum.myapplication.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<FavoritesLoadState>(FavoritesLoadState.Initial)
    val favoritesState = _favoritesState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoritesState.update { FavoritesLoadState.Loading }
            try {
                val tracks = playlistsRepository.getFavoriteTracks()
                _favoritesState.update { FavoritesLoadState.Content(tracks) }
            } catch (e: Exception) {
                _favoritesState.update { FavoritesLoadState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    fun toggleFavorite(track: Track, currentState: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentState) {
                playlistsRepository.removeFromFavorites(track.id)
            } else {
                playlistsRepository.addToFavorites(track)
            }
            _isFavorite.update { !currentState }
            loadFavorites()
        }
    }

    fun checkFavoriteStatus(trackId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.update { playlistsRepository.isFavorite(trackId) }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(Creator.getPlaylistRepository()) as T
                }
            }
    }
}
