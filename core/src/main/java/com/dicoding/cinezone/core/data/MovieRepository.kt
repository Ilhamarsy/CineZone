package com.dicoding.cinezone.core.data

import com.dicoding.cinezone.core.data.source.local.LocalDataSource
import com.dicoding.cinezone.core.data.source.remote.RemoteDataSource
import com.dicoding.cinezone.core.data.source.remote.network.ApiResponse
import com.dicoding.cinezone.core.data.source.remote.response.MovieResponse
import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.core.domain.repository.IMovieRepository
import com.dicoding.cinezone.core.utils.AppExecutors
import com.dicoding.cinezone.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovie().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun searchMovie(title: String): Flow<List<Movie>> {
        return localDataSource.searchMovie(title).map { DataMapper.mapEntitiesToDomain(it) }
    }

}