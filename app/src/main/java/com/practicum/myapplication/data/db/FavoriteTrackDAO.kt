package com.practicum.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.myapplication.data.entity.FavoriteTrackEntity

@Dao
interface FavoriteTrackDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteTrackEntity: FavoriteTrackEntity)

    @Query("DELETE FROM favorite_track WHERE trackId = :trackId")
    suspend fun removeFromFavorites(trackId: Long)

    @Query("SELECT trackId FROM favorite_track ORDER BY addedAt DESC")
    suspend fun getFavoriteTrackIds(): List<Long>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_track WHERE trackId = :trackId)")
    suspend fun isFavorite(trackId: Long): Boolean
}
