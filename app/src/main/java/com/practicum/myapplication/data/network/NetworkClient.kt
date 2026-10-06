package com.practicum.myapplication.data.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}