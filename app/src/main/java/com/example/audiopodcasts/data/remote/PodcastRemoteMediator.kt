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

            val responseEntity = podcastDb.responseDao.getResponseEntity("podcastId")

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    if (responseEntity == null || responseEntity.hasNextPage.not()) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    responseEntity.currentPage.plus(1)
                }
            }

            /**
             * Checking here if the responseEntity is null (first app launch or database is empty),
             *      only then we need to make an API call, otherwise
             *      use the database to load the data.
             *
             *      This way, we won't upsert the database entries on each
             *          paging invalidation.
             *      It ensures the update operation of the podcasts to favourite
             *          works and paging happens from local database entries as expected.
             *          -- 10 items are loaded per page.
             */

            if (responseEntity == null) {

                val podcastDto = podcastApi.getBestPodcasts(page = loadKey)
                val podcasts = podcastDto.body()?.podcasts

                podcastDb.withTransaction {

                    if (loadType == LoadType.REFRESH) {
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
            }

            MediatorResult.Success(endOfPaginationReached = false)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}