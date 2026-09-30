package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.data.dto.TrackDTO

class PlaylistStorage {

    private val playlistStorage = mutableListOf<PlaylistDTO>(
        PlaylistDTO("Best songs 2021", "98 треков", hasImage = true),
        PlaylistDTO("Summer Party", "157 треков", hasImage = true),
        PlaylistDTO("Morning", "32 трека", hasImage = false)
    )

    fun createNewPlaylist(playlist: PlaylistDTO) {
        playlistStorage.add(playlist)
    }

    fun getPlaylists(): List<PlaylistDTO> {
        return playlistStorage
    }

    fun addTrackToPlaylist(track: TrackDTO, trackId: Long) {
        TODO()
    }
}