package com.practicum.myapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.data.db.SearchHistoryPreferences
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryPreferences: SearchHistoryPreferences
) : ViewModel() {
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchHistory = MutableStateFlow(listOf<String>())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        loadHistory()
    }

    var selectedTrack: Track? = null
        private set
    fun setSelectedTrack(track: Track) {
        selectedTrack = track
    }

    fun saveSearchTextToHistory(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryPreferences.addEntry(text)
            _searchHistory.value = searchHistoryPreferences.getEntries()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchHistory.value = searchHistoryPreferences.getEntries()
        }
    }

    fun search(whatSearch: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val list = tracksRepository.searchTracks(expression = whatSearch)
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: Exception){
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                        Creator.getTracksRepository(),
                        Creator.getSearchHistoryPreferences()
                        ) as T
                }
            }
    }
}
