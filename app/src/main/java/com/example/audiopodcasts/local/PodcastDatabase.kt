package com.example.audiopodcasts.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PodcastEntity::class, ResponseEntity::class],
    version = 1
)
abstract class PodcastDatabase: RoomDatabase() {

    abstract val podcastDao: PodcastDao
    abstract val responseDao: ResponseDao
}