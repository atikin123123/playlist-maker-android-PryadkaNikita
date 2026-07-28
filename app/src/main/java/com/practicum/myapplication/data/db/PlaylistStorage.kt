//package com.practicum.myapplication.data.db
//
//import com.practicum.myapplication.data.dto.PlaylistEntity
//import com.practicum.myapplication.data.dto.TrackEntity
//
//class PlaylistStorage {
//
//    private val MINUTE_MS = 60_000
//    private val playlistEntityStorages = mutableListOf<PlaylistEntity>(
//        PlaylistEntity(
//            id = 1,
//            title = "Best songs 2021",
//            year = "2022",
//            coverUrl = null, // TODO: реальный URL
//            trackEntities = mutableListOf<TrackEntity>(),                                        // TODO: подставить треки
//            trackCount = 98,
//            sumTracksDurationMillis = 300 * MINUTE_MS,                   // 300 минут со скриншота
//            hasImage = true
//        ),
//        PlaylistEntity(
//            id = 2,
//            title = "Summer Party",
//            year = "2022",                                               // TODO: неизвестно
//            coverUrl = null,    // TODO: реальный URL
//            trackEntities = mutableListOf<TrackEntity>(),
//            trackCount = 157,
//            sumTracksDurationMillis = 157 * 3 * MINUTE_MS,               // TODO: оценка ~3 мин на трек
//            hasImage = true
//        ),
//        PlaylistEntity(
//            id = 3,
//            title = "Morning",
//            year = "2022",                                               // TODO: неизвестно
//            coverUrl = null,
//            trackEntities = mutableListOf(TrackEntity(
//                id = 1,
//                trackName = "Танцевать",
//                artistName = "Мельница",
//                trackTimeMillis = 222000
//            ),
//                TrackEntity(
//                    id = 2,
//                    trackName = "Чёрный бумер",
//                    artistName = "Серега",
//                    trackTimeMillis = 241000
//                )),
//            trackCount = 32,
//            sumTracksDurationMillis = 32 * 3 * MINUTE_MS,                // TODO: оценка ~3 мин на трек
//            hasImage = false
//        ),
//    )
//
//    fun createNewPlaylist(playlistEntity: PlaylistEntity) {
//        playlistEntityStorages.add(playlistEntity)
//    }
//
//    fun getPlaylists(): List<PlaylistEntity> {
//        return playlistEntityStorages
//    }
//
//    fun addTrackToPlaylist(trackEntity: TrackEntity, trackId: Long) {
//        TODO()
//    }
//}