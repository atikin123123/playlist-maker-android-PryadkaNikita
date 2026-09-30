package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.db.TrackStorage

class RetrofitNetworkClient(private val trackStorage: TrackStorage) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val searchList = trackStorage.search((dto as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}