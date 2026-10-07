package com.practicum.myapplication.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.myapplication.R
import com.practicum.myapplication.ui.track.TrackListItem

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onTrackClick: () -> Unit,
    viewModel: SearchViewModel
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    fun runSearch(query: String) {
        if (query.isNotBlank()) {
            text = query
            viewModel.saveSearchTextToHistory(query)
            viewModel.search(query)
            focusManager.clearFocus()
        }
    }

    BackHandler(enabled = isFocused) { focusManager.clearFocus() }

    val suggestions = searchHistory.filter { it.contains(text, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white))
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.description_back),
                    tint = colorResource(R.color.black)
                )
            }
            Text(
                text = stringResource(R.string.search),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.black),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize().padding(top = 68.dp)) {
                when (screenState) {
                    is SearchState.Initial -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.search_start_hint),
                                color = colorResource(R.color.yp_gray)
                            )
                        }
                    }
                    is SearchState.Searching -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = colorResource(R.color.yp_blue))
                        }
                    }
                    is SearchState.Success -> {
                        val tracks = (screenState as SearchState.Success).list

                        if (tracks.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = stringResource(R.string.nothing_found),
                                    color = colorResource(R.color.yp_gray),
                                    fontSize = 18.sp
                                )
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(tracks.size) { index ->
                                    TrackListItem(track = tracks[index], onClick = { track -> viewModel.setSelectedTrack(track); onTrackClick() }, viewModel = viewModel)
                                }
                            }
                        }
                    }
                    is SearchState.Fail -> {
                        val error = (screenState as SearchState.Fail).error
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.error_prefix) + " " + error,
                                color = colorResource(R.color.yp_red)
                            )
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(10.dp),
                color = colorResource(R.color.yp_light_gray)
            ) {
                Column {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { isFocused = it.isFocused },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(android.R.color.transparent),
                            unfocusedContainerColor = colorResource(android.R.color.transparent),
                            focusedIndicatorColor = colorResource(android.R.color.transparent),
                            unfocusedIndicatorColor = colorResource(android.R.color.transparent),
                            cursorColor = colorResource(R.color.yp_blue)
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.search_placeholder),
                                color = colorResource(R.color.yp_gray)
                            )
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = colorResource(R.color.black)),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { runSearch(text) }),
                        leadingIcon = {
                            IconButton(onClick = { runSearch(text) }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.description_search),
                                    tint = colorResource(R.color.yp_gray)
                                )
                            }
                        },
                        trailingIcon = {
                            if (text.isNotEmpty()) {
                                IconButton(onClick = { text = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = stringResource(R.string.description_clear),
                                        tint = colorResource(R.color.yp_gray)
                                    )
                                }
                            }
                        }
                    )

                    AnimatedVisibility(visible = isFocused && suggestions.isNotEmpty()) {
                        Column {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                thickness = 0.5.dp,
                                color = colorResource(R.color.yp_gray)
                            )
                            suggestions.forEach { item ->
                                HistoryItem(text = item, onClick = { runSearch(item) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null,
            tint = colorResource(R.color.yp_gray),
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = colorResource(R.color.black)
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen({}, {}, viewModel = viewModel(factory = SearchViewModel.getViewModelFactory()))
}