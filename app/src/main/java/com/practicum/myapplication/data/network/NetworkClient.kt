package com.practicum.myapplication.data.network

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}