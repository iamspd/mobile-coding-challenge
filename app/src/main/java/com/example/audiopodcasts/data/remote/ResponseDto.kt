package com.example.audiopodcasts.data.remote

import com.squareup.moshi.Json

data class ResponseDto(
    @field:Json(name = "id") val responseId: Int,
    val podcasts: List<PodcastDto>,
    @field:Json(name = "has_next") val hasNextPage: Boolean,
    @field:Json(name = "page_number") val currentPage: Int
)
