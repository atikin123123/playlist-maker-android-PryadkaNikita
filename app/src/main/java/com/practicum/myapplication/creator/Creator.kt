package com.practicum.myapplication.creator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.practicum.myapplication.data.db.AppDatabase
import com.practicum.myapplication.data.db.FavoriteTrackDAO
import com.practicum.myapplication.data.db.SearchHistoryPreferences
import com.practicum.myapplication.data.repository.PlaylistsRepositoryImpl
import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.repository.TracksRepository

object Creator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val tracksRepository by lazy {
        TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun getTracksRepository(): TracksRepository = tracksRepository

    private val database by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val playlistRepository by lazy {
        PlaylistsRepositoryImpl(database)
    }
    fun getPlaylistRepository(): PlaylistsRepository = playlistRepository

    private val _favoriteTrackDAO by lazy {
        database.getFavoriteTrackDAO()
    }
    fun getFavoriteTrackDAO(): FavoriteTrackDAO = _favoriteTrackDAO

    private val Context.searchHistoryDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "search_history_prefs"
    )

    private val _searchHistoryPreferences by lazy {
        SearchHistoryPreferences(appContext.searchHistoryDataStore)
    }

    fun getSearchHistoryPreferences(): SearchHistoryPreferences = _searchHistoryPreferences
}
