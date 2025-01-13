package com.example.audiopodcasts.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responseEntity")
data class ResponseEntity(
    @PrimaryKey val id: String,
    val responseId: Int,
    val hasNextPage: Boolean,
    val currentPage: Int
)
