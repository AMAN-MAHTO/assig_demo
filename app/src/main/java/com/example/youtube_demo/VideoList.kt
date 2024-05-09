package com.example.youtube_demo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViedoList(viewModel: MainViewModel, navController: NavHostController) {
    val list_video = viewModel.list_videos.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Video Player")})
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =  Modifier.padding(it)){

            SearchView(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),state = textState, placeHolder = "")

            LazyColumn(
                contentPadding = PaddingValues(4.dp)
            ){
                items(list_video.value.filter {
                    it.title.contains(textState.value.text, ignoreCase = true) ||
                            it.description.contains(textState.value.text, ignoreCase = true)
                }, key = {it.id}){

                    PreviewListCard(video = it, { navController.navigate(Screen.VideoPlayer.name+"/${it}")})

                }
            }
        }
    }


}


@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    placeHolder: String
) {

    OutlinedTextField(
        label = { Text("Search") },
        leadingIcon = {Icon(imageVector = Icons.Filled.Search, contentDescription = "",Modifier.padding(end = 8.dp))},

        modifier = modifier,
        value = state.value,
        onValueChange = { value ->
            state.value = value
        })

        }

@Preview
@Composable
fun ser() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    SearchView(modifier = Modifier.padding(16.dp),state = textState, placeHolder = "")

}

@Composable
fun PreviewListCard(video:Video, listCardOnClick:(id:Int)->Unit) {
    Card(modifier = Modifier
        .padding(bottom = 13.dp)
        .clickable {
            listCardOnClick(video.id)
            Log.d("Video", "PreviewListCard_id: ${video.id}")

        }) {
        AsyncImage(
            model = "https://tdwzdjjjdzdoxbpbsese.supabase.co/storage/v1/object/public/thumbnail/${video.thumbnail}.png",
            contentDescription = "Translated description of what the image contains"
        )
        Text(video.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(13.dp))
    }

}