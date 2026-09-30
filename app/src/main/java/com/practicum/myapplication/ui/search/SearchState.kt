package com.practicum.myapplication.ui.search

import com.practicum.myapplication.domain.model.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
}