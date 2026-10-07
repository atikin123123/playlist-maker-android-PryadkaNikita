package com.practicum.myapplication.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.domain.model.Playlist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    onBack: () -> Unit,
    onCreatePlaylistClick: () -> Unit,
    onPlaylistClick: () -> Unit
) {

    val screenState by playlistsViewModel.playlistsState.collectAsState()
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var playlistToDelete by remember { mutableStateOf<Playlist?>(null) }

    LaunchedEffect(Unit) {
        playlistsViewModel.loadPlaylists()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.playlists_screen_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yp_text_primary)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.description_back),
                            tint = colorResource(R.color.yp_text_primary)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.white)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreatePlaylistClick() },
                shape = CircleShape,
                containerColor = colorResource(R.color.yp_gray_placeholder),
                contentColor = colorResource(R.color.white)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_playlist_content_description),
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = colorResource(R.color.white)
    ) { paddingValues ->

        when (screenState) {
            PlaylistsLoadState.Initial, PlaylistsLoadState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     CircularProgressIndicator(color = colorResource(R.color.yp_gray_loader))
                }

            is PlaylistsLoadState.Success -> {
                val playlists = (screenState as PlaylistsLoadState.Success).list

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = 8.dp)
                ) {
                    items(playlists) { playlist ->
                        PlaylistItem(
                            playlist = playlist,
                            onClick = { playlist ->
                                playlistsViewModel.setSelectedPlaylist(playlist)
                                onPlaylistClick()
                            },
                            onLongClick = {
                                playlistToDelete = playlist
                                showDeleteConfirmDialog = true
                            },
                            playlistsViewModel = playlistsViewModel
                        )
                    }
                }
            }

            is PlaylistsLoadState.Fail -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.error_loading_playlists), color = colorResource(R.color.yp_red))
            }

        }

    }

    if (showDeleteConfirmDialog && playlistToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text(stringResource(R.string.delete_playlist_title)) },
            text = { Text(stringResource(R.string.delete_playlist_message, playlistToDelete?.title ?: "")) },
            confirmButton = {
                TextButton(
                    onClick = {
                        playlistToDelete?.let {
                            playlistsViewModel.deletePlaylist(it)
                        }
                        showDeleteConfirmDialog = false
                        playlistToDelete = null
                    }
                ) {
                    Text(stringResource(R.string.delete), color = colorResource(R.color.yp_red))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistItem(
    playlist: Playlist,
    onClick: (Playlist) -> Unit,
    onLongClick: () -> Unit,
    playlistsViewModel: PlaylistsViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .combinedClickable(
                onClick = { onClick(playlist) },
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (playlist.hasImage) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = stringResource(R.string.playlist_cover_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.yp_gray_placeholder)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.no_playlist_cover_content_description),
                    tint = colorResource(R.color.yp_gray_placeholder)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = playlist.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.yp_text_primary),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = playlist.trackCount,
                fontSize = 14.sp,
                color = colorResource(R.color.yp_gray_text_secondary)
            )
        }
    }
}

//@Preview
//@Composable
//fun PlaylistScreenPreview() {
//    PlaylistsScreen(onBack = {}, onCreatePlaylistClick = {})
//}