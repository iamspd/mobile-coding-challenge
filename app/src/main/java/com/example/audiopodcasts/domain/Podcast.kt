package com.example.audiopodcasts.domain

data class Podcast(
    val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val isFavourite: Boolean = false
)
