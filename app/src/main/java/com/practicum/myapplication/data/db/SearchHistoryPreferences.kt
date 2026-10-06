package com.practicum.myapplication.data.db

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(
        CoroutineName("search-historypreferences") + SupervisorJob()
    )
) {
    companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
        private val preferencesKey = stringPreferencesKey("search_history")
    }

    fun addEntry(word: String) {
        val entry = word.trim()
        if (entry.isEmpty()) return

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val history = preferences[preferencesKey].toHistory().toMutableList()
                history.remove(entry)      // убираем дубликат, если он есть
                history.add(0, entry)      // свежий запрос идёт первым
                preferences[preferencesKey] =
                    history.take(MAX_ENTRIES).joinToString(SEPARATOR)
            }
        }
    }

    suspend fun getEntries(): List<String> {
        return dataStore.data.first()[preferencesKey]
            .orEmpty()
            .split(SEPARATOR)
            .filter { it.isNotEmpty() }
    }

    private fun String?.toHistory(): List<String> =
        if (isNullOrEmpty()) emptyList() else split(SEPARATOR)
}