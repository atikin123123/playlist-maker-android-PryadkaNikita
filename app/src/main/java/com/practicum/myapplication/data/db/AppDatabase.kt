package com.practicum.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.myapplication.data.entity.PlaylistEntity
import com.practicum.myapplication.data.entity.PlaylistTrackEntity
import com.practicum.myapplication.data.entity.TrackEntity

@Database(
    version = 1,
    entities = [
        PlaylistEntity::class,
        TrackEntity::class,
        PlaylistTrackEntity::class
    ]
)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun getPlaylistDAO(): PlaylistDAO
    abstract fun getTrackDAO(): TrackDAO
    abstract fun getPlaylistTrackDAO(): PlaylistTrackDAO
}