package com.practicum.myapplication.ui.track

import androidx.compose.foundation.background
import com.practicum.myapplication.ui.playlist.PlaylistItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.ui.playlist.PlaylistsLoadState
import com.practicum.myapplication.ui.playlist.PlaylistsViewModel

@Composable
fun AddToPlaylistScreen(
//    playlists: List<Playlist>,
    onPlaylistSelected: () -> Unit,
    modifier: Modifier = Modifier,
    playlistsViewModel: PlaylistsViewModel
) {

    val screenState by playlistsViewModel.playlistsState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
//            .padding(top = 16.dp)
    ) {
        Text(
            text = "Добавить в плейлист",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF191C1F), // Темно-серый/почти черный цвет как на макете
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))


        when (screenState) {
            PlaylistsLoadState.Initial, PlaylistsLoadState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF9D9D9D))
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
                            PlaylistItem(playlist = playlist, onPlaylistSelected, playlistsViewModel)
                        }
                    }
                }
            }

            is PlaylistsLoadState.Fail -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Не удалось загрузить плейлисты", color = Color.Red)
            }

        }
        // Список плейлистов

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