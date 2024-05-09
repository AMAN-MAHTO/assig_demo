package com.example.youtube_demo

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerPage(viewModel: MainViewModel, id:Int, onClickBackButton:()->Unit) {
    val scrollState = rememberScrollableState(consumeScrollDelta = {delta -> delta})
    val video = viewModel.list_videos.value.filter {
        it.id == id
    }
    Scaffold(
        topBar = { TopAppBar(title = {Text(text = "VideoPlayer $id")},
            navigationIcon = { IconButton(onClick = { onClickBackButton()}) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            } })}
    ) {
        Column(modifier = Modifier
            .padding(it)
            .scrollable(scrollState, orientation = Orientation.Vertical)){

        VideoPlayer(url = Uri.parse("https://tdwzdjjjdzdoxbpbsese.supabase.co/storage/v1/object/public/videos/${video[0].video_id}.mp4"))

            Text(video[0].title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(13.dp))
            Text(text = "Description", style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 13.dp,end = 13.dp,top = 0.dp,bottom = 4.dp), maxLines = 10)
            Text(video[0].description, style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(13.dp))
        }

    }
}


@Composable
fun VideoPlayer( url:Uri) {
    AndroidView( factory = { context ->
        VideoView(context).apply {

            setVideoURI(url)

            val myController = MediaController(context)
            myController.setAnchorView(this)
            setMediaController(myController)

            setOnPreparedListener{
                start()
            }

        }
    })
}