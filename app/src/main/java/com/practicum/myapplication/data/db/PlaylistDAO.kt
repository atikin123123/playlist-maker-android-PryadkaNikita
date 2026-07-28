package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practicum.myapplication.data.dto.PlaylistEntity

@Dao
interface PlaylistDAO {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewPlaylist(playlistEntity: PlaylistEntity)

    @Update(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Delete(entity = PlaylistEntity::class)
    fun deletePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlist WHERE id = :id")
    fun getPlaylistById(id: Long): PlaylistEntity

    @Query("SELECT * FROM playlist")
    fun getPlaylists(): List<PlaylistEntity>
}
