package com.practicum.myapplication.ui.favorites

import com.practicum.myapplication.domain.model.Track

sealed class FavoritesLoadState {
    object Initial : FavoritesLoadState()
    object Loading : FavoritesLoadState()
    data class Content(val tracks: List<Track>) : FavoritesLoadState()
    data class Error(val message: String) : FavoritesLoadState()
}
