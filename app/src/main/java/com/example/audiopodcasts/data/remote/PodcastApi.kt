package com.example.audiopodcasts.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastApi {

    /**
     * Base URL constant of test listen notes API
     */

    companion object {
        const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"
    }

    /**
     * The endpoint {BASE_URL/best_podcasts} returns the [com.example.audiopodcasts.data.remote.ResponseDto]
     *    @param page takes an Integer value that returns
     *          the podcast list on a given page number
     */

    @GET("best_podcasts")
    suspend fun getBestPodcasts(
        @Query("page") page: Int
    ): Response<ResponseDto>


    /**
     * This endpoint {BASE_URL/podcasts/id} returns the hard-coded data from the
     *      test api, hence not going to use this one.
     *      Podcast details will be loaded from the database.
     */

    @GET("/podcasts/{id}")
    suspend fun getPodcastById(id: String): Response<PodcastDto>
}