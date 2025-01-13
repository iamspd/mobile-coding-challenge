package com.example.audiopodcasts.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.audiopodcasts.data.remote.PodcastApi
import com.example.audiopodcasts.data.remote.PodcastRemoteMediator
import com.example.audiopodcasts.local.PodcastDatabase
import com.example.audiopodcasts.local.PodcastEntity
import com.example.audiopodcasts.repository.PodcastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePodcastDatabase(@ApplicationContext context: Context): PodcastDatabase {
        return Room.databaseBuilder(
            context,
            PodcastDatabase::class.java,
            "podcasts.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePodcastApi(): PodcastApi {
        return Retrofit.Builder()
            .baseUrl(PodcastApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePodcastPager(
        podcastDb: PodcastDatabase,
        podcastApi: PodcastApi
    ): Pager<Int, PodcastEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PodcastRemoteMediator(
                podcastDb = podcastDb,
                podcastApi = podcastApi
            ),
            pagingSourceFactory = {
                podcastDb.podcastDao.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun providePodcastRepository(database: PodcastDatabase): PodcastRepository {
        return PodcastRepository(database)
    }
}