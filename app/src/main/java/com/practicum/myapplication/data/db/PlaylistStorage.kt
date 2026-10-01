package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.data.dto.TrackDTO

class PlaylistStorage {

    private val MINUTE_MS = 60_000
    private val playlistStorage = mutableListOf<PlaylistDTO>(
        PlaylistDTO(
            id = 1,
            title = "Best songs 2021",
            year = "2022",
            coverUrl = null, // TODO: реальный URL
            tracks = mutableListOf<TrackDTO>(),                                        // TODO: подставить треки
            trackCount = 98,
            sumTracksDurationMillis = 300 * MINUTE_MS,                   // 300 минут со скриншота
            hasImage = true
        ),
        PlaylistDTO(
            id = 2,
            title = "Summer Party",
            year = "2022",                                               // TODO: неизвестно
            coverUrl = null,    // TODO: реальный URL
            tracks = mutableListOf<TrackDTO>(),
            trackCount = 157,
            sumTracksDurationMillis = 157 * 3 * MINUTE_MS,               // TODO: оценка ~3 мин на трек
            hasImage = true
        ),
        PlaylistDTO(
            id = 3,
            title = "Morning",
            year = "2022",                                               // TODO: неизвестно
            coverUrl = null,
            tracks = mutableListOf(TrackDTO(
                id = 1,
                trackName = "Танцевать",
                artistName = "Мельница",
                trackTimeMillis = 222000
            ),
                TrackDTO(
                    id = 2,
                    trackName = "Чёрный бумер",
                    artistName = "Серега",
                    trackTimeMillis = 241000
                )),
            trackCount = 32,
            sumTracksDurationMillis = 32 * 3 * MINUTE_MS,                // TODO: оценка ~3 мин на трек
            hasImage = false
        ),
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