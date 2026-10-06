package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.db.AppDatabase
import com.practicum.myapplication.data.entity.PlaylistEntity
import com.practicum.myapplication.data.entity.PlaylistTrackEntity
import com.practicum.myapplication.data.entity.toDomain
import com.practicum.myapplication.data.entity.toEntity
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track
import java.util.Calendar

class PlaylistsRepositoryImpl(
    private val appDatabase: AppDatabase
) : PlaylistsRepository {

    private val _playlistDAO = appDatabase.getPlaylistDAO()
    private val _trackDAO = appDatabase.getTrackDAO()
    private val _playlistTrackDAO = appDatabase.getPlaylistTrackDAO()

    override suspend fun createPlaylist(title: String, description: String) {
        val newPlaylist = PlaylistEntity(
            title = title,
            year = Calendar.getInstance().get(Calendar.YEAR).toString(),
            sumDurationMillis = 0,
            hasImage = true
        )
        _playlistDAO.insertNewPlaylist(newPlaylist)
    }

    override suspend fun getPlaylists(): List<Playlist> {
        return _playlistDAO.getPlaylists().map {
            val trackIds = _playlistTrackDAO.getTrackIdsByPlaylistId(it.id)

            val tracks = _trackDAO.getTracksByIds(trackIds)
            it.toDomain(tracks)
        }
    }

    override suspend fun addTrackToPlaylist(
        track: Track,
        playlist: Playlist
    ) {
        val playlistTrackEntity = PlaylistTrackEntity(playlist.id, track.id)
        _playlistTrackDAO.addTrackToPlaylist(playlistTrackEntity, track.toEntity())
    }

    override suspend fun getTracksByPlaylistId(id: Long): List<Track> {
        val trackIds = _playlistTrackDAO.getTrackIdsByPlaylistId(id)

        return _trackDAO.getTracksByIds(trackIds).map { it.toDomain() }
    }

    override suspend fun updatePlaylistSumTrackTimeMillisById(id: Long) {
        val trackIds = _playlistTrackDAO.getTrackIdsByPlaylistId(id)

        val playlistNewSumTrackTimeMillis = _trackDAO.getTracksByIds(trackIds).sumOf { it.trackTimeMillis }

        val playlist = _playlistDAO.getPlaylistById(id)

        val newPlaylist = PlaylistEntity(
            id = id,
            title = playlist.title,
            year = playlist.year,
            sumDurationMillis = playlistNewSumTrackTimeMillis,
            hasImage = playlist.hasImage,
        )

        _playlistDAO.updatePlaylist(newPlaylist)
    }
}