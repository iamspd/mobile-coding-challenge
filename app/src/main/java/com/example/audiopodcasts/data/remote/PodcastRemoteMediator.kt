package com.example.audiopodcasts.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.audiopodcasts.data.mappers.toPodcastEntity
import com.example.audiopodcasts.data.mappers.toResponseEntity
import com.example.audiopodcasts.local.PodcastDatabase
import com.example.audiopodcasts.local.PodcastEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator(
    private val podcastDb: PodcastDatabase,
    private val podcastApi: PodcastApi
) : RemoteMediator<Int, PodcastEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PodcastEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val responseEntity = podcastDb.responseDao.getResponseEntity("podcastId")
                    if (responseEntity == null || responseEntity.hasNextPage.not()) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    responseEntity.currentPage.plus(1)
                }
            }

            val podcastDto = podcastApi.getBestPodcasts(page = loadKey)
            val podcasts = podcastDto.body()?.podcasts
            val hasNextPage = podcastDto.body()?.hasNextPage

            podcastDb.withTransaction {

                if(loadType == LoadType.REFRESH) {
                    podcastDb.podcastDao.clearAll()
                    podcastDb.responseDao.clearAll()
                }

                val podcastEntities = podcasts?.map {
                    it.toPodcastEntity()
                }

                if (podcastEntities != null) {
                    podcastDb.podcastDao.upsertPodcasts(podcastEntities)
                }

                if (podcastDto.body() != null) {
                    podcastDb.responseDao.upsertResponse(podcastDto.body()!!.toResponseEntity())
                }
            }
            MediatorResult.Success(endOfPaginationReached = !hasNextPage!!)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}