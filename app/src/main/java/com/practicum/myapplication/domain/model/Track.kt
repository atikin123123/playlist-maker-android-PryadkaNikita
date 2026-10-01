package com.practicum.myapplication.domain.model

import androidx.compose.ui.graphics.Color

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val coverUrl: String? = null,
    val borderColor: Color? = null,
)