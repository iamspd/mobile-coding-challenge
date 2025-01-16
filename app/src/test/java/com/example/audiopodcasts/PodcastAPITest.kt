package com.example.audiopodcasts

import com.example.audiopodcasts.data.remote.PodcastApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PodcastAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var podcastApi: PodcastApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        podcastApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(PodcastApi::class.java)
    }

    /**
     * Test that endpoints are working.
     */

    @Test
    fun testGetBestPodcast_returnApiResponse() = runBlocking {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource(filename = "/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = podcastApi.getBestPodcasts(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.hasNextPage)
    }


    @Test
    fun testGetBestPodcasts_returnListOfPodcasts() = runBlocking {
        val mockResponse = MockResponse()
        val content = Helper.readFileResource(filename = "/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = podcastApi.getBestPodcasts(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(2, response.body()!!.podcasts.size)
    }

    @Test
    fun testGetBestPodcasts_returnErrorMessage() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("Something went wrong")
        mockResponse.setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val response = podcastApi.getBestPodcasts(1)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}