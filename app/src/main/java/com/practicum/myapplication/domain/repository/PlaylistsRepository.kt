package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.data.entity.PlaylistEntity
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track

interface PlaylistsRepository {
    suspend fun createPlaylist(title: String, description: String)
    suspend fun getPlaylists(): List<Playlist>
    suspend fun addTrackToPlaylist(track: Track, playlist: Playlist)
    suspend fun getTracksByPlaylistId(id: Long): List<Track>
    suspend fun updatePlaylistSumTrackTimeMillisById(id: Long)
    suspend fun addToFavorites(track: Track)
    suspend fun removeFromFavorites(trackId: Long)
    suspend fun getFavoriteTracks(): List<Track>
    suspend fun isFavorite(trackId: Long): Boolean
}