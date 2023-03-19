package com.dicoding.cinezone.core.data.source.remote.network

import com.dicoding.cinezone.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getData(
        @Query("api_key") api_key: String
    ): ListMovieResponse
}