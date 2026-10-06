package com.practicum.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.myapplication.domain.model.Playlist

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val year: String,
    val sumDurationMillis: Int,
    val hasImage: Boolean
)

fun PlaylistEntity.toDomain(tracks: List<TrackEntity>): Playlist {
    val tracks = tracks.map { it.toDomain() }
    return Playlist(
        id = id,
        title = title,
        year = year,
        coverUrl = null,
        tracks = tracks,
        trackCount = tracks.size.toTrackCountString(),
        sumTrackDuration = sumDurationMillis.toDurationString(),
        hasImage = hasImage
    )
}

fun Playlist.toEntity(trackEntities: List<TrackEntity>): PlaylistEntity = PlaylistEntity(
    id = id,
    title = title,
    year = year,
    sumDurationMillis = trackEntities.sumOf { it.trackTimeMillis },
    hasImage = hasImage
)

private fun Int.toDurationString(): String {
    val totalMinutes = this / 60_000
    val hours = totalMinutes / 60
    val minutes = totalMinutes - hours * 60
    return if (hours > 0) "$hours ч $minutes мин" else "$minutes мин"
}

private fun Int.toTrackCountString(): String {
    val lastTwoDigits = this % 100
    val lastDigit = this % 10
    val word = when {
        lastTwoDigits in 11..14 -> "треков"
        lastDigit == 1 -> "трек"
        lastDigit in 2..4 -> "трека"
        else -> "треков"
    }
    return "$this $word"
}
