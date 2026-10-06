package com.practicum.myapplication.ui.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.domain.model.Track
import com.practicum.myapplication.ui.search.SearchViewModel

@Composable
fun TrackListItem(
    track: Track,
    onClick: (Track) -> Unit,
    onLongClick: () -> Unit = {},
    viewModel: SearchViewModel
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(track) },
                onLongClick = onLongClick
            )
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
            Text(
                track.trackName,
                fontSize = 16.sp,
                color = colorResource(R.color.black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal
            )
            Text(
                "${track.artistName} • ${track.trackTime}",
                fontSize = 11.sp,
                color = colorResource(R.color.yp_gray),
                maxLines = 1
            )
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colorResource(R.color.yp_gray))
    }
}

//@Preview
//@Composable
//fun TLPreview() {
//    TrackListItem(Track("Fuel", "Metallica", "5:32"),
//        {},
//        viewModel = viewModel(factory = SearchViewModel.getViewModelFactory()))
//}