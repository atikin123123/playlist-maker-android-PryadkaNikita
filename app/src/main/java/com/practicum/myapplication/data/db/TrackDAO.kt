package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.myapplication.data.dto.TrackEntity

@Dao
interface TrackDAO {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertTrack(trackEntity: TrackEntity)

    @Query("select * from track where id between :from and :to")
    fun getTracksBetween(from: Int, to: Int): List<TrackEntity>
}