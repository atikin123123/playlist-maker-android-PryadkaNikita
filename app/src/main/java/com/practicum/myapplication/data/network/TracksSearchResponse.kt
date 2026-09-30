package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.TrackDTO

class TracksSearchResponse(val results: List<TrackDTO>) : BaseResponse()