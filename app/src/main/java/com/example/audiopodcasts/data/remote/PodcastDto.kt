package com.example.audiopodcasts.data.remote

import com.squareup.moshi.Json

data class PodcastDto(
    val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    @field:Json(name = "image") val imageUrl: String,
    @field:Json(name = "thumbnail") val thumbnailUrl: String,
    val isFavourite: Boolean?
)