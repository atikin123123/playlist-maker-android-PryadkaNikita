package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.dto.TrackDTO
import com.practicum.myapplication.data.db.RoomDBClient
import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.model.Playlist

class PlaylistsRepositoryImpl(
    private val roomDBClient: RoomDBClient
) : PlaylistsRepository {
    override suspend fun createPlaylist(title: String, description: String) {
        roomDBClient.createPlaylist(PlaylistDTO(title, "0", false))
    }

    override suspend fun getPlaylists(): List<Playlist> {
        return roomDBClient.getPlaylists().map {
            Playlist(it.title, it.tracksCount, it.hasImage)
        }
    }

    override suspend fun addTrackToPlaylist(
        track: TrackDTO,
        playlistId: Long
    ) {
        TODO("Not yet implemented")
    }
}