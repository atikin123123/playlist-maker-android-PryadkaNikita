package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.data.dto.TrackDTO

interface DBClient {
    suspend fun getPlaylists(): List<PlaylistDTO>

    suspend fun createPlaylist(playlistDTO: PlaylistDTO)

    suspend fun addTrackToPlaylist(track: TrackDTO, playlistId: Long)
}