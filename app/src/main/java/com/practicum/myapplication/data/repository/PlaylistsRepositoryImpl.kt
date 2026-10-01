package com.practicum.myapplication.data.repository

import android.text.format.Time
import com.practicum.myapplication.data.dto.TrackDTO
import com.practicum.myapplication.data.db.RoomDBClient
import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track

class PlaylistsRepositoryImpl(
    private val roomDBClient: RoomDBClient
) : PlaylistsRepository {
    override suspend fun createPlaylist(title: String, description: String) {
        roomDBClient.createPlaylist(PlaylistDTO(
            id = roomDBClient.getPlaylists().maxOfOrNull { it.id + 1 } ?: 1,
            title = title,
            year = Time.YEAR.toString(),
            coverUrl = null,
            tracks = mutableListOf<TrackDTO>(),
            trackCount = 0,
            sumTracksDurationMillis = 0,
            hasImage = true
            ))
    }

    override suspend fun getPlaylists(): List<Playlist> {
        return roomDBClient.getPlaylists().map { dto ->
            val seconds = dto.sumTracksDurationMillis / 1000
            val minutes = seconds / 60
            val sumTracksDuration = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)

            Playlist(
                id = dto.id,
                title = dto.title,
                year = dto.year,
                coverUrl = dto.coverUrl,
                tracks = dto.tracks.map { mapToDomain(it) },
                trackCount = dto.trackCount.toString(),
                sumTrackDuration = sumTracksDuration,
                hasImage = dto.hasImage
            )
        }
    }

    override suspend fun addTrackToPlaylist(
        track: Track,
        playlistId: Long
    ) {
        val trackDTO = mapToDTO(track)
        val playlists = roomDBClient.getPlaylists()
        var flag = false

        for (playlist in playlists) {
            if (playlist.id == playlistId) {
                if (!playlist.tracks.contains<TrackDTO>(trackDTO)) {
                    playlist.tracks.add(mapToDTO(track))
                    flag = true
                }
            }
        }

        if (!flag) {
            throw IllegalArgumentException("no playlist with provided id")
        }


    }

    private fun mapToDomain(dto: TrackDTO): Track {
        val seconds = dto.trackTimeMillis / 1000
        val minutes = seconds / 60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
        return Track(
            id = dto.id, // Generate or fetch proper ID
            trackName = dto.trackName,
            artistName = dto.artistName,
            trackTime = trackTime
        )
    }

    private fun mapToDTO(track: Track): TrackDTO {
        // Parse trackTime back to millis if needed, or store differently
        return TrackDTO(
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