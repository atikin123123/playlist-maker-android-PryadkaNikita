package com.practicum.myapplication.data.dto

data class TrackDTO(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int
)