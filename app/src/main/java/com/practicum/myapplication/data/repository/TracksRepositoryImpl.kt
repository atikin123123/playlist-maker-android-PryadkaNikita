package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.network.TracksSearchRequest
import com.practicum.myapplication.data.network.TracksSearchResponse
import com.practicum.myapplication.data.network.NetworkClient
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.domain.model.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(id = it.id, trackName = it.trackName, artistName =  it.artistName, trackTime = trackTime)
            }
        } else {
            emptyList()
        }
    }
}