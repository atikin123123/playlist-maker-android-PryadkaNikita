package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.db.AppDatabase
import com.practicum.myapplication.data.dto.PlaylistEntity
import com.practicum.myapplication.data.dto.PlaylistTrackEntity
import com.practicum.myapplication.data.dto.TrackEntity
import com.practicum.myapplication.data.dto.toDomain
import com.practicum.myapplication.data.dto.toEntity
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track
import java.util.Calendar

class PlaylistsRepositoryImpl(
    private val appDatabase: AppDatabase

) : PlaylistsRepository {

    fun getAppDatabase(): AppDatabase = appDatabase

    override suspend fun createPlaylist(title: String, description: String) {
        val newPlaylist = PlaylistEntity(
            title = title,
            year = Calendar.getInstance().get(Calendar.YEAR).toString(),
//            trackEntities = mutableListOf<TrackEntity>(),
            sumDurationMillis = 0,
            hasImage = true
        )
        appDatabase.getPlaylistDAO().insertNewPlaylist(newPlaylist)
    }

    override suspend fun getPlaylists(): List<Playlist> {
        return appDatabase.getPlaylistDAO().getPlaylists().map {
            val trackCount = appDatabase.getPlaylistTrackDAO().getTrackIdsByPlaylistId(it.id).size
            it.toDomain(trackCount)
        }
    }

    override suspend fun addTrackToPlaylist(
        track: Track,
        playlist: Playlist
    ) {
        val playlistTrackEntity = PlaylistTrackEntity(playlist.id, track.id)
        appDatabase.getPlaylistTrackDAO().addTrackToPlaylist(playlistTrackEntity, track.toEntity())
    }

    override suspend fun getTracksByPlaylistId(id: Long): List<Track> {
        val trackIds = appDatabase.getPlaylistTrackDAO().getTrackIdsByPlaylistId(id)
        val from = trackIds.min()
        val to = trackIds.max()

        return appDatabase.getTrackDAO().getTracksBetween(from, to).map { it.toDomain() }
    }

    private fun mapToDomain(dto: TrackEntity): Track {
        val seconds = dto.trackTimeMillis / 1000
        val minutes = seconds / 60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
        return Track(
            id = dto.id,
            trackName = dto.trackName,
            artistName = dto.artistName,
            trackTime = trackTime
        )
    }

    private fun mapToDTO(track: Track): TrackEntity {
        // Parse trackTime back to millis if needed, or store differently
        return TrackEntity(
            id = track.id,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = parseTrackTimeToMillis(track.trackTime)
        )
    }

    private fun parseTrackTimeToMillis(trackTime: String): Int {
        val parts = trackTime.split(":")
        val minutes = parts[0].toInt()
        val seconds = parts[1].toInt()
        return (minutes * 60 + seconds) * 1000
    }
}