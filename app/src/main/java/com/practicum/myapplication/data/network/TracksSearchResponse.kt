package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.entity.TrackEntity

class TracksSearchResponse(val results: List<TrackEntity>) : BaseResponse()