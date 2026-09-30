package com.practicum.myapplication.data.db

import com.practicum.myapplication.data.dto.TrackDTO
import java.util.Locale

class TrackStorage {
    private val listTracks = listOf(
        TrackDTO(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000 // 2:38
        ),
        TrackDTO(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000 // 4:43
        ),
        TrackDTO(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000 // 5:12
        ),
        TrackDTO(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackDTO(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackDTO(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackDTO(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackDTO(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackDTO(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackDTO(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000
        )
    )

    fun search(request: String): List<TrackDTO> {
        return listTracks.filter {
            it.trackName.lowercase(Locale.ROOT).contains(request.lowercase(Locale.ROOT))
            ||
            it.artistName.lowercase(Locale.ROOT).contains(request.lowercase(Locale.ROOT))
        }
    }
}