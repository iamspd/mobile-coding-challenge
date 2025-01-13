package com.example.audiopodcasts.data.mappers

import com.example.audiopodcasts.data.remote.PodcastDto
import com.example.audiopodcasts.data.remote.ResponseDto
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.local.PodcastEntity
import com.example.audiopodcasts.local.ResponseEntity

fun PodcastDto.toPodcastEntity(): PodcastEntity {
    return PodcastEntity(
        id = id,
        title = title,
        publisher = publisher,
        description = description,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite
    )
}

fun ResponseDto.toResponseEntity(): ResponseEntity {
    return ResponseEntity(
        id = "podcastId",
        responseId = responseId,
        hasNextPage = hasNextPage,
        currentPage = currentPage
    )
}

/**
 * the data is always fetched from database to avoid multiple network requests
 * --- single source of truth ---
 */

fun PodcastEntity.toPodcast(): Podcast {
    return Podcast(
        id = id,
        title = title,
        publisher = publisher,
        description = description,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite
    )
}

fun Podcast.toPodcastEntity(): PodcastEntity {
    return PodcastEntity(
        id = id,
        title = title,
        publisher = publisher,
        description = description,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite
    )
}