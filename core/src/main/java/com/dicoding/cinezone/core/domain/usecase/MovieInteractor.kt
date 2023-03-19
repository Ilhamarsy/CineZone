package com.dicoding.cinezone.core.domain.usecase

import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.core.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getMovie() = movieRepository.getMovie()

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)

    override fun searchMovie(title: String) = movieRepository.searchMovie(title)

}