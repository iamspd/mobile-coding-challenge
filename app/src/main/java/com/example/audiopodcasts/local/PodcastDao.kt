package com.example.audiopodcasts.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface PodcastDao {

    @Upsert
    suspend fun upsertPodcasts(podcasts: List<PodcastEntity>)

    @Query("SELECT * FROM podcastEntity")
    fun pagingSource(): PagingSource<Int, PodcastEntity>

    @Query("SELECT * FROM podcastEntity WHERE id = :podcastId")
    suspend fun getPodcastById(podcastId: String): PodcastEntity

    @Update
    suspend fun updatePodcast(podcast: PodcastEntity)

    @Query("DELETE FROM podcastEntity")
    suspend fun clearAll()
}