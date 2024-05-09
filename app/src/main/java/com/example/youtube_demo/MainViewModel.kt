package com.example.youtube_demo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _list_videos = MutableStateFlow(listOf<Video>())
    val list_videos = _list_videos.asStateFlow()

    init {
        viewModelScope.launch {
            val result = supabase.from("Videos").select().decodeList<Video>()
            _list_videos.value= result
            Log.d("Videos", "ViedoList: $result")
        }
    }
}