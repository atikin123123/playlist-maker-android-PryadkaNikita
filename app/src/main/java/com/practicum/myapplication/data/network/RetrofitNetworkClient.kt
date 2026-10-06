package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.entity.TrackEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ITunesApi::class.java)

    override suspend fun doRequest(dto: Any): TracksSearchResponse {
        val request = dto as TracksSearchRequest
        val response = api.searchTracks(term = request.expression)
        return TracksSearchResponse(
            results = response.results.map { dto ->
                TrackEntity(
                    id = dto.trackId,
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTimeMillis = dto.trackTimeMillis ?: 0
                )
            }
        ).apply { resultCode = 200 }
    }
}