package com.practicum.myapplication.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaylistDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val year: String,
    val coverUrl: String?,
    val tracks: MutableList<TrackDTO>,
    val trackCount: Int,
    val sumTracksDurationMillis: Int,
    val hasImage: Boolean
)
