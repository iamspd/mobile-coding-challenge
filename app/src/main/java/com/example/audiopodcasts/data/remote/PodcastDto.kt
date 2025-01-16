package com.example.audiopodcasts.data.remote

import com.squareup.moshi.Json

data class PodcastDto(
    val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    @field:Json(name = "image") val imageUrl: String, // collecting image URL for the image in PodcastDetailsScreen
    @field:Json(name = "thumbnail") val thumbnailUrl: String, // image URL for thumbnail in PodcastListItem
    val isFavourite: Boolean = false
)