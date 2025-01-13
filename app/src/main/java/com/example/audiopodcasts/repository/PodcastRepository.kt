package com.example.audiopodcasts.repository

import android.util.Log
import com.example.audiopodcasts.data.mappers.toPodcast
import com.example.audiopodcasts.data.mappers.toPodcastEntity
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.local.PodcastDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PodcastRepository @Inject constructor(
    private val podcastDatabase: PodcastDatabase
) {

    suspend fun getPodcastById(podcastId: String): Flow<Podcast> {
        return flow {
            try {
                val podcast = podcastDatabase.podcastDao.getPodcastById(podcastId = podcastId)
                emit(podcast.toPodcast())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updatePodcast(podcastId: String) {
        return getPodcastById(podcastId = podcastId).collect { podcast ->
            podcastDatabase.podcastDao.updatePodcast(
                podcast.copy(isFavourite = podcast.isFavourite?.not()).toPodcastEntity()
            )
        }
    }
}