package com.dicoding.cinezone.core.domain.usecase

import com.dicoding.cinezone.core.data.Resource
import com.dicoding.cinezone.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun searchMovie(title: String): Flow<List<Movie>>

}