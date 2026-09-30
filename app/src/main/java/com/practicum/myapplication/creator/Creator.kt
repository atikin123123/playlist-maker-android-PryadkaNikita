package com.practicum.myapplication.creator

import com.practicum.myapplication.data.db.PlaylistStorage
import com.practicum.myapplication.data.db.RoomDBClient
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

    private val playlistStorage by lazy { PlaylistStorage() }
    private val playlistRepository by lazy {
        PlaylistsRepositoryImpl(RoomDBClient(playlistStorage))
    }

    fun getPlaylistRepository(): PlaylistsRepository = playlistRepository
}