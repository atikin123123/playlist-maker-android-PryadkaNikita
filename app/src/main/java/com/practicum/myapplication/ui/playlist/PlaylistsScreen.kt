package com.practicum.myapplication.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.myapplication.domain.model.Playlist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.getViewModelFactory()),
    onBack: () -> Unit,
    onCreatePlaylistClick: () -> Unit
) {

    val screenState by playlistsViewModel.playlistsState.collectAsState()

    LaunchedEffect(Unit) {
        playlistsViewModel.loadPlaylists()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Плейлисты",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreatePlaylistClick() },
                shape = CircleShape,
                containerColor = Color(0xFFC4C4C4), // Светло-серый цвет кнопки из макета
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить плейлист",
                    modifier = Modifier.size(32.dp) // Увеличенный размер плюсика
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End, // Кнопка справа внизу
        containerColor = Color.White
    ) { paddingValues ->

        when (screenState) {
            PlaylistsLoadState.Initial, PlaylistsLoadState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF9D9D9D))
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
                        PlaylistItem(playlist)
                    }
                }
            }

            is PlaylistsLoadState.Fail -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Не удалось загрузить плейлисты", color = Color.Red)
            }

        }

    }
}

@Composable
fun PlaylistItem(playlist: Playlist) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Обложка плейлиста
        if (playlist.hasImage) {
            Image(
                // ЗАМЕНИТЕ НА ВАШУ КАРТИНКУ ИЗ РЕСУРСОВ
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Обложка плейлиста",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            // Плейсхолдер, если обложки нет (как у плейлиста "Morning")
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                // Если есть своя иконка заглушки (например, R.drawable.ic_placeholder) - используйте Image
                Icon(
                    imageVector = Icons.Default.Add, // Временно используем плюс
                    contentDescription = "Нет обложки",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Текст (Название и количество треков)
        Column {
            Text(
                text = playlist.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = playlist.tracksCount,
                fontSize = 14.sp,
                color = Color(0xFFAAAAAA) // Серый цвет текста
            )
        }
    }
}


@Preview
@Composable
fun PlaylistScreenPreview() {
    PlaylistsScreen(onBack = {}, onCreatePlaylistClick = {})
}