package com.repository.repository

import com.repository.models.TrendingRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("repositories")
    suspend fun getTrendingRepository(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLanguageCode: String
    ): Response<List<TrendingRepo>>
}