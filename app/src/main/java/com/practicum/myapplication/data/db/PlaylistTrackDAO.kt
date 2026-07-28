package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.practicum.myapplication.data.dto.PlaylistTrackEntity
import com.practicum.myapplication.data.dto.TrackEntity

@Dao
interface PlaylistTrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrack(playlistTrackEntity: PlaylistTrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Transaction
    suspend fun addTrackToPlaylist(
        playlistTrackEntity: PlaylistTrackEntity,
        trackEntity: TrackEntity
    ) {
        insertTrack(trackEntity)
        insertPlaylistTrack(playlistTrackEntity)
    }

    @Query("select trackId from playlist_track where playlistId = :playlistId")
    suspend fun getTrackIdsByPlaylistId(playlistId: Long) : List<Int>
}