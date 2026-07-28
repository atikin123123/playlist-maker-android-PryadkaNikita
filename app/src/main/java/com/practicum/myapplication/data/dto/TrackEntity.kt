package com.practicum.myapplication.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.myapplication.domain.model.Track

@Entity(tableName = "track")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int
)

fun TrackEntity.toDomain(): Track {
    val seconds = trackTimeMillis / 1000
    val minutes = seconds / 60
    return Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = "%02d:%02d".format(minutes, seconds - minutes * 60)
    )
}

fun Track.toEntity(): TrackEntity = TrackEntity(
    id = id,
    trackName = trackName,
    artistName = artistName,
    trackTimeMillis = trackTime.toMillis()
)

fun String.toMillis(): Int {
    val parts = split(":")
    val minutes = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val seconds = parts.getOrNull(1)?.toIntOrNull() ?: 0
    return (minutes * 60 + seconds) * 1000
}