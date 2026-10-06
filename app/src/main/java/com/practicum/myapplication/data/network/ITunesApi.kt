package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.entity.TrackEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("search")
    suspend fun searchTracks(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("limit") limit: Int = 50
    ): ITunesSearchResponse
}

data class ITunesSearchResponse(
    val resultCount: Int,
    val results: List<ITunesTrackDto>
)

data class ITunesTrackDto(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int?,
    val artworkUrl100: String?
)
