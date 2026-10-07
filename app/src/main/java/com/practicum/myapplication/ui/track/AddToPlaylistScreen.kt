package com.practicum.myapplication.ui.track

import androidx.compose.foundation.background
import com.practicum.myapplication.ui.playlist.PlaylistItem
import com.practicum.myapplication.domain.model.Playlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.ui.playlist.PlaylistsLoadState
import com.practicum.myapplication.ui.playlist.PlaylistsViewModel

@Composable
fun AddToPlaylistScreen(
    onPlaylistSelected: (Playlist) -> Unit,
    modifier: Modifier = Modifier,
    playlistsViewModel: PlaylistsViewModel
) {

    val screenState by playlistsViewModel.playlistsState.collectAsState()

    LaunchedEffect(Unit) {
        playlistsViewModel.loadPlaylists()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white))
    ) {
        Text(
            text = stringResource(R.string.add_to_playlist_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.yp_black),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))


        when (screenState) {
            PlaylistsLoadState.Initial, PlaylistsLoadState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     CircularProgressIndicator(color = colorResource(R.color.yp_gray_loader))
                }

            is PlaylistsLoadState.Success -> {
                val playlists = (screenState as PlaylistsLoadState.Success).list

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(playlists) { playlist ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            PlaylistItem(playlist = playlist, onClick = { playlist -> onPlaylistSelected(playlist) }, onLongClick = {}, playlistsViewModel = playlistsViewModel)
                        }
                    }
                }
            }

            is PlaylistsLoadState.Fail -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.error_loading_playlists), color = colorResource(R.color.yp_red))
            }

        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun AddToPlaylistScreenPreview() {
//    val mockPlaylists = listOf(
//        Playlist("Best songs 2021", "98 треков", hasImage = true),
//        Playlist("Summer Party", "157 треков", hasImage = true),
//        Playlist("Morning", "32 трека", hasImage = false)
//    )
//
//    AddToPlaylistScreen(
//        playlists = mockPlaylists,
//        onPlaylistSelected = {}
//    )
//}