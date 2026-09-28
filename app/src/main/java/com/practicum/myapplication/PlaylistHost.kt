package com.practicum.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.myapplication.ui.activity.MainScreen
import com.practicum.myapplication.ui.search.SearchScreen
import com.practicum.myapplication.ui.settings.SettingsScreen
import com.practicum.myapplication.ui.settings.MediaLibraryScreen
import com.practicum.myapplication.ui.settings.FavoritesScreen

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
                onBack = { navController.popBackStack() }
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
            MediaLibraryScreen()
        }

        // 5. Экран Избранного
        composable(Screen.Favorites.name) {
            FavoritesScreen()
        }
    }
}