package com.dicoding.cinezone.core.data.source.local

import com.dicoding.cinezone.core.data.source.local.entity.MovieEntity
import com.dicoding.cinezone.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getMovie(): Flow<List<MovieEntity>> = movieDao.getMovie()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun searchMovie(title: String): Flow<List<MovieEntity>> = movieDao.searchMovie(title)

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.setFavoriteMovie(movie)
    }
}