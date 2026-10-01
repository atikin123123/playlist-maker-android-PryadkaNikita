package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.data.dto.TrackDTO
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track

interface PlaylistsRepository {
    suspend fun createPlaylist(title: String, description: String)
    suspend fun getPlaylists(): List<Playlist>
    suspend fun addTrackToPlaylist(track: Track, playlistId: Long)
}