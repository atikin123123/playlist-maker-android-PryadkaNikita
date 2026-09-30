package com.practicum.myapplication.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.myapplication.R
import com.practicum.myapplication.domain.model.Track

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onTrackClick: () -> Unit,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    fun runSearch() {
        if (text.isNotEmpty()) {
            viewModel.search(text)
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white))
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

        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.yp_light_gray),
                    unfocusedContainerColor = colorResource(R.color.yp_light_gray),
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

                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),

                leadingIcon = {
                    IconButton(onClick = { runSearch() }) {
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
        }

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
                            TrackListItem(track = tracks[index], onTrackClick)
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
}

@Composable
fun TrackListItem(track: Track, onClick: () -> Unit) {
//    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
//                val message = context.getString(R.string.message_track_clicked)
//                Toast.makeText(context, "$message ${track.trackName}", Toast.LENGTH_SHORT).show()
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = stringResource(R.string.description_track_image),
            modifier = Modifier.size(45.dp).clip(RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(track.trackName, fontSize = 16.sp, color = colorResource(R.color.black), maxLines = 1, fontWeight = FontWeight.Normal)
            Text("${track.artistName} • ${track.trackTime}", fontSize = 11.sp, color = colorResource(R.color.yp_gray), maxLines = 1)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colorResource(R.color.yp_gray))
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen({}, {})
}

@Preview
@Composable
fun TLPreview() {
    TrackListItem(Track("Fuel", "Metallica", "5:32"), {})
}