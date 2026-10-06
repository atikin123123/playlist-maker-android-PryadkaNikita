package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.practicum.myapplication.data.entity.PlaylistTrackEntity
import com.practicum.myapplication.data.entity.TrackEntity

@Dao
interface PlaylistTrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrack(playlistTrackEntity: PlaylistTrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Delete
    suspend fun deletePlaylistTrack(playlistTrackEntity: PlaylistTrackEntity)

    @Query("select trackId from playlist_track where playlistId = :playlistId")
    suspend fun getTrackIdsByPlaylistId(playlistId: Long) : List<Long>

    @Query("DELETE FROM playlist_track WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Transaction
    suspend fun addTrackToPlaylist(
        playlistTrackEntity: PlaylistTrackEntity,
        trackEntity: TrackEntity
    ) {
        insertTrack(trackEntity)
        insertPlaylistTrack(playlistTrackEntity)
    }
}