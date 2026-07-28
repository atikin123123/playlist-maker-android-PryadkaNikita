package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.TrackEntity
import java.util.Locale

class TrackStorage {
    private val listTrackEntities = listOf(
        TrackEntity(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000 // 2:38
        ),
        TrackEntity(
            id = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000 // 4:43
        ),
        TrackEntity(
            id = 3,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000 // 5:12
        ),
        TrackEntity(
            id = 4,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackEntity(
            id = 4,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackEntity(
            id = 5,
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackEntity(
            id = 6,
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackEntity(
            id = 7,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackEntity(
            id = 8,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackEntity(
            id = 9,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000
        )
    )

    fun search(request: String): List<TrackEntity> {
        return listTrackEntities.filter {
            it.trackName.lowercase(Locale.ROOT).contains(request.lowercase(Locale.ROOT))
            ||
            it.artistName.lowercase(Locale.ROOT).contains(request.lowercase(Locale.ROOT))
        }
    }
}