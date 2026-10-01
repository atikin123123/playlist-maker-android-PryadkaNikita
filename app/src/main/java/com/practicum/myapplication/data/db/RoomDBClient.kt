package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.data.dto.TrackDTO
import com.practicum.myapplication.domain.model.Track

class RoomDBClient(
    private val playlistStorage: PlaylistStorage
) : DBClient {

    override suspend fun getPlaylists(): List<PlaylistDTO> = playlistStorage.getPlaylists()

    override suspend fun createPlaylist(playlistDTO: PlaylistDTO) {
        playlistStorage.createNewPlaylist(playlistDTO)
    }

    override suspend fun addTrackToPlaylist(
        track: Track,
        playlistId: Long
    ) {
        TODO("Not yet implemented")
    }


}