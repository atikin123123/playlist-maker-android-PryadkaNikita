package com.practicum.myapplication.ui.track

import com.practicum.myapplication.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.domain.model.Playlist
import com.practicum.myapplication.ui.favorites.FavoritesViewModel
import com.practicum.myapplication.ui.playlist.PlaylistsViewModel
import com.practicum.myapplication.ui.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetails(
    onBack: () -> Unit,
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val track: Track = searchViewModel.selectedTrack ?: throw NullPointerException()

    val isFav by favoritesViewModel.isFavorite.collectAsState()

    LaunchedEffect(track.id) {
        favoritesViewModel.checkFavoriteStatus(track.id)
    }

    // Цвета для светлой темы
    val backgroundColor = colorResource(R.color.white)
    val textColor = colorResource(R.color.black)
    val secondaryTextColor = colorResource(R.color.yp_gray)
    val buttonBackgroundColor = colorResource(R.color.yp_light_gray)
    val iconColor = colorResource(R.color.white)

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Кнопка Назад
        IconButton(
            onClick = { onBack() },
            modifier = Modifier.offset(x = (-12).dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = textColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Обложка альбома
        Image(
            // ЗАМЕНИТЕ R.drawable.beatles_cover НА ВАШУ КАРТИНКУ
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Обложка альбома",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Делает картинку квадратной
                .clip(RoundedCornerShape(8.dp)), // Скругление углов как на макете
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Название трека
        Text(
            text = track.trackName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Исполнитель
        Text(
            text = track.artistName,
            fontSize = 16.sp,
            color = textColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Блок с круглыми кнопками (Добавить и Лайк)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Кнопка "Добавить в плейлист"
            IconButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .size(56.dp)
                    .background(color = buttonBackgroundColor, shape = CircleShape)
            ) {
                Icon(
                    // Использована стандартная иконка плюса.
                    // Замените на painterResource(id = R.drawable.your_custom_icon), если есть своя
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Добавить в плейлист",
                    tint = iconColor
                )
            }

            // Кнопка "Лайк/Избранное"
            IconButton(
                onClick = {
                    favoritesViewModel.toggleFavorite(track, isFav)
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(color = buttonBackgroundColor, shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Нравится",
                    tint = if (isFav) Color.Red else iconColor
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                AddToPlaylistScreen(
                    playlistsViewModel = playlistsViewModel,
                    onPlaylistSelected = { playlist ->
                        playlistsViewModel.addTrackToPlaylist(track, playlist)
                        showBottomSheet = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Блок "Длительность"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Длительность",
                fontSize = 14.sp,
                color = secondaryTextColor
            )

            Text(
                text = track.trackTime,
                fontSize = 14.sp,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

//@Preview
//@Composable
//fun TDPreview() {
//    TrackDetails(onBack = {}, TODO())
//}