package com.example.audiopodcasts.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "podcastEntity")
data class PodcastEntity(
    @PrimaryKey val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val isFavourite: Boolean = false
)
