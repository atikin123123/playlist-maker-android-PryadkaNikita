package com.practicum.myapplication.ui.playlist

import com.practicum.myapplication.domain.model.Playlist

sealed class PlaylistsLoadState {
    object Initial: PlaylistsLoadState()
    object Loading: PlaylistsLoadState()
    data class Success(val list: List<Playlist>): PlaylistsLoadState()
    data class Fail(val error: String): PlaylistsLoadState()
}