package com.practicum.myapplication.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.ui.search.SearchViewModel
import com.practicum.myapplication.ui.track.TrackListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    searchViewModel: SearchViewModel,
    onBack: () -> Unit,
    onTrackClick: () -> Unit
) {
    val screenState by viewModel.favoritesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.favorites),
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
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        when (screenState) {
            FavoritesLoadState.Initial, FavoritesLoadState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF9D9D9D))
                }
            }

            is FavoritesLoadState.Error -> {
                Text(
                    text = (screenState as FavoritesLoadState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is FavoritesLoadState.Content -> {
                val tracks = (screenState as FavoritesLoadState.Content).tracks
                if (tracks.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.screen_favorites_content),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    }
                } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(tracks, key = { it.id }) { track ->
                                TrackListItem(
                                    track = track,
                                    onClick = { track -> searchViewModel.setSelectedTrack(track); onTrackClick() },
                                    viewModel = searchViewModel
                                )
                            }
                        }
                }
            }
        }
    }
}
