package com.dicoding.cinezone.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie, state: Boolean) = movieUseCase.setFavoriteMovie(movie, state)
}