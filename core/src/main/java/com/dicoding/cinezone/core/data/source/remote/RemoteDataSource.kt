package com.dicoding.cinezone.core.data.source.remote

import android.util.Log
import com.dicoding.cinezone.core.BuildConfig.API_KEY
import com.dicoding.cinezone.core.data.source.remote.network.ApiResponse
import com.dicoding.cinezone.core.data.source.remote.network.ApiService
import com.dicoding.cinezone.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovie(): Flow<ApiResponse<List<MovieResponse>>> {

        return flow {
            try {
                val response = apiService.getData(API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())

            }
        }.flowOn(Dispatchers.IO)
    }
}