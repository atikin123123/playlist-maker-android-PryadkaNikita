package com.practicum.myapplication.ui.playlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.ui.search.SearchViewModel
import com.practicum.myapplication.ui.track.TrackListItem


private val TextPrimary = Color(0xFF1A1A1A)
private val IconGray = Color(0xFFA9A9AE)


@Composable
fun PlaylistScreen(
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onCoverClick: () -> Unit = {},
    onTrackClick: () -> Unit = {},
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel
) {
    val playlist by playlistsViewModel.selectedPlaylistState.collectAsState()
    val currentPlaylist = playlist ?: throw NullPointerException()
    var showDeleteTrackDialog by remember { mutableStateOf(false) }
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = TextPrimary,
                    )
                }
            }
            item {
                PlaylistCover(
                    url = currentPlaylist.coverUrl,
                    onClick = onCoverClick,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                )
            }
            item { PlaylistInfo(currentPlaylist, onMenuClick) }
            items(currentPlaylist.tracks, key = { it.id }) { track ->
                TrackListItem(
                    track = track,
                    onClick = { track -> searchViewModel.setSelectedTrack(track); onTrackClick() },
                    onLongClick = {
                        trackToDelete = track
                        showDeleteTrackDialog = true
                    },
                    viewModel = searchViewModel
                )
            }
        }
    }

    if (showDeleteTrackDialog && trackToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteTrackDialog = false },
            title = { Text("Удалить трек?") },
            text = { Text("Трек \"${trackToDelete?.trackName}\" будет удалён из плейлиста.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        trackToDelete?.let {
                            playlistsViewModel.removeTrackFromPlaylist(it, currentPlaylist)
                        }
                        showDeleteTrackDialog = false
                        trackToDelete = null
                    }
                ) {
                    Text("Удалить", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteTrackDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Composable
private fun PlaylistCover(
    url: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = "Обложка плейлиста",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Icon(
                imageVector = Icons.Filled.AddPhotoAlternate,
                contentDescription = "Добавить обложку",
                tint = IconGray,
                modifier = Modifier.size(88.dp),
            )
        }
    }
}

@Composable
private fun PlaylistInfo(
    playlist: Playlist,
    onMenuClick: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = playlist.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )
        Spacer(Modifier.height(10.dp))
        Text(text = playlist.year, fontSize = 20.sp, color = TextPrimary)
        Spacer(Modifier.height(10.dp))
        Text(
            text = "${playlist.sumTrackDuration} : ${playlist.trackCount}",
            fontSize = 20.sp,
            color = TextPrimary
        )
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .padding(top = 8.dp)
                .offsetStart(),
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Меню",
                tint = TextPrimary,
            )
        }
        Spacer(Modifier.height(4.dp))
    }
}

// небольшой сдвиг, чтобы иконка «⋮» визуально выровнялась по левому краю текста
private fun Modifier.offsetStart(): Modifier = this.then(Modifier.padding(start = 0.dp))

@Composable
private fun TrackCover(track: Track) {
    val shape = RoundedCornerShape(2.dp)
    val base = Modifier.size(48.dp)
    if (track.coverUrl != null) {
        AsyncImage(
            model = track.coverUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = base
                .clip(shape)
                .let {
                    if (track.borderColor != null) {
                        it.border(BorderStroke(2.dp, track.borderColor), shape)
                    } else it
                },
        )
    } else {
        Box(modifier = base, contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .border(BorderStroke(2.dp, Color(0xFF555555)), RoundedCornerShape(3.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null,
                    tint = Color(0xFF555555),
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

// ---------- Preview ----------

private val previewTracks = listOf(
    Track(1, "Yesterday (Remastered 2009)", "The Beatles", "2:55", null, Color.Red),
    Track(
        2,
        "Here Comes The Sun (Remastered 2009)",
        "The Beatles",
        "2:55",
        null,
        Color(0xFF1E4FA8)
    ),
    Track(3, "No Reply", "The Beatles", "5:41"),
    Track(4, "Let It Be", "The Beatles", "3:11"),
)

//private val previewPlaylist = Playlist(
//    id = 101,
//    title = "Best songs 2021",
//    year = "2022",
//    trackCount = "98 треков",
//    sumTrackDuration = "300 минут",
//    coverUrl = null,
//    tracks = previewTracks,
//    hasImage = true
//)

//@Preview(showBackground = true, heightDp = 800)
//@Composable
//private fun PlaylistScreenEmptyCoverPreview() {
//    MaterialTheme { PlaylistScreen(playlist = previewPlaylist) }
//}
//
//@Preview(showBackground = true, heightDp = 800)
//@Composable
//private fun PlaylistScreenWithCoverPreview() {
//    MaterialTheme {
//        PlaylistScreen(playlist = previewPlaylist.copy(coverUrl = "https://example.com/cover.jpg"))
//    }
//}