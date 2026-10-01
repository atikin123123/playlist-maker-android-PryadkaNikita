package com.practicum.myapplication.domain.model

import com.practicum.myapplication.domain.model.Track

data class Playlist(
    val id: Long,
    val title: String,
    val year: String,
    val coverUrl: String?,
    val tracks: List<Track>,
    val trackCount: String,
    val sumTrackDuration: String,
    val hasImage: Boolean
)