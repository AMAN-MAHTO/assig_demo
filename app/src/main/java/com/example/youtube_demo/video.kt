package com.example.youtube_demo

import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Video(
    val id: Int,

    val video_id: String,
    val title: String,
    val description: String,
    val likes: Int,
    val channel_name: String,
    val thumbnail:String,

)
