package com.practicum.myapplication.creator

import com.practicum.myapplication.data.db.AppDatabase
import com.practicum.myapplication.data.db.TrackStorage
import com.practicum.myapplication.data.repository.PlaylistsRepositoryImpl
import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.repository.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(TrackStorage()))
    }

    private val playlistRepository by lazy {
        PlaylistsRepositoryImpl(TODO())
    }

    fun getPlaylistRepository(): PlaylistsRepository = playlistRepository
}