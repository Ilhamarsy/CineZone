package com.dicoding.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.cinezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getFavoriteMovie().asLiveData()

}