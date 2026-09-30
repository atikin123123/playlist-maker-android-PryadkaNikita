package com.practicum.myapplication.ui.track

import androidx.compose.foundation.background
import com.practicum.myapplication.ui.playlist.PlaylistItem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.data.dto.PlaylistDTO
import com.practicum.myapplication.domain.model.Playlist

@Composable
fun AddToPlaylistScreen(
    playlists: List<Playlist>,
    onPlaylistSelected: (Playlist) -> Unit,
    modifier: Modifier = Modifier
) {
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

        // Список плейлистов
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(playlists) { playlist ->
                // Обертка clickable для обработки нажатия на элемент списка,
                // если сам PlaylistItem не обрабатывает клики.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPlaylistSelected(playlist) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    PlaylistItem(playlist = playlist)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddToPlaylistScreenPreview() {
    val mockPlaylists = listOf(
        Playlist("Best songs 2021", "98 треков", hasImage = true),
        Playlist("Summer Party", "157 треков", hasImage = true),
        Playlist("Morning", "32 трека", hasImage = false)
    )

    AddToPlaylistScreen(
        playlists = mockPlaylists,
        onPlaylistSelected = {}
    )
}