package com.practicum.myapplication

import androidx.compose.foundation.content.PlatformTransferableContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.myapplication.ui.activity.MainScreen
import com.practicum.myapplication.ui.favorites.FavoritesScreen
import com.practicum.myapplication.ui.favorites.FavoritesViewModel
import com.practicum.myapplication.ui.playlist.CreatePlaylistScreen
import com.practicum.myapplication.ui.playlist.PlaylistScreen
import com.practicum.myapplication.ui.playlist.PlaylistsScreen
import com.practicum.myapplication.ui.playlist.PlaylistsViewModel
import com.practicum.myapplication.ui.search.SearchScreen
import com.practicum.myapplication.ui.search.SearchViewModel
import com.practicum.myapplication.ui.settings.SettingsScreen
import com.practicum.myapplication.ui.track.TrackDetails

@Composable
fun PlaylistHost(navController: NavHostController) {

    val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
    val playlistsViewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.getViewModelFactory())
    val favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.getViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = Screen.Main.name
    ) {
        // 1. Главный экран
        composable(Screen.Main.name) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.Search.name) },
                onSettingsClick = { navController.navigate(Screen.Settings.name) },
                onLibraryClick = { navController.navigate(Screen.MediaLibrary.name) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.name) }
            )
        }

        // 2. Экран Поиска
        composable(Screen.Search.name) {
            SearchScreen(
                viewModel = searchViewModel,
                onBack = { navController.popBackStack() },
                onTrackClick = {
                    navController.navigate(Screen.TrackDetails.name)
                }
            )
        }

        // 3. Экран Настроек
        composable(Screen.Settings.name) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // 4. Экран Медиатеки
        composable(Screen.MediaLibrary.name) {
            PlaylistsScreen(
                playlistsViewModel = playlistsViewModel,
                onBack = { navController.popBackStack() },
                onCreatePlaylistClick = {navController.navigate(Screen.CreatePlaylistScreen.name)},
                onPlaylistClick = { navController.navigate(Screen.PlaylistScreen.name) }
                )
        }

        // 5. Экран Избранного
        composable(Screen.Favorites.name) {
            FavoritesScreen(
                viewModel = favoritesViewModel,
                searchViewModel = searchViewModel,
                onBack = { navController.popBackStack() },
                onTrackClick = { navController.navigate(Screen.TrackDetails.name) }
            )
        }

        // 6. Экран Детали Трека
        composable(Screen.TrackDetails.name) {
            TrackDetails(
                playlistsViewModel = playlistsViewModel,
                searchViewModel = searchViewModel,
                favoritesViewModel = favoritesViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // 7. Экран Создания Плейлиста
        composable(Screen.CreatePlaylistScreen.name) {
            CreatePlaylistScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // 7. Экран Плейлиста
        composable(Screen.PlaylistScreen.name) {
            PlaylistScreen(
                onBack = { navController.popBackStack() },
                onTrackClick = { navController.navigate(Screen.TrackDetails.name)},
                playlistsViewModel = playlistsViewModel,
                searchViewModel = searchViewModel
                
            )
        }
    }
}