package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.myapplication.data.entity.TrackEntity

@Dao
interface TrackDAO {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Query("select * from track where id in (:ids)")
    suspend fun getTracksByIds(ids: List<Long>): List<TrackEntity>
}