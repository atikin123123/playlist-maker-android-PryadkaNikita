package com.practicum.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.myapplication.ui.activity.MainScreen
import com.practicum.myapplication.ui.playlist.CreatePlaylistScreen
import com.practicum.myapplication.ui.search.SearchScreen
import com.practicum.myapplication.ui.settings.SettingsScreen
import com.practicum.myapplication.ui.playlist.PlaylistsScreen
import com.practicum.myapplication.ui.settings.FavoritesScreen
import com.practicum.myapplication.ui.track.TrackDetails

@Composable
fun PlaylistHost(navController: NavHostController) {

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
                onBack = { navController.popBackStack() },
                onTrackClick = { navController.navigate(Screen.TrackDetails.name) }
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
                onBack = { navController.popBackStack() },
                onCreatePlaylistClick = {navController.navigate(Screen.CreatePlaylistScreen.name)}
                )
        }

        // 5. Экран Избранного
        composable(Screen.Favorites.name) {
            FavoritesScreen()
        }

        // 6. Экран Детали Трека
        composable(Screen.TrackDetails.name) {
            TrackDetails(
                onBack = { navController.popBackStack() }
            )
        }

        // 7. Экран Создания Плейлиста
        composable(Screen.CreatePlaylistScreen.name) {
            CreatePlaylistScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}