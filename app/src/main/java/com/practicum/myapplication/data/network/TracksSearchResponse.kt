package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.TrackEntity

class TracksSearchResponse(val results: List<TrackEntity>) : BaseResponse()