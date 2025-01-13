package com.example.audiopodcasts.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastApi {

    companion object {
        const val BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"
    }

    @GET("best_podcasts")
    suspend fun getBestPodcasts(
        @Query("page") page: Int
    ): Response<ResponseDto>

    @GET("/podcasts/{id}")
    suspend fun getPodcastById(id: String): Response<PodcastDto>
}