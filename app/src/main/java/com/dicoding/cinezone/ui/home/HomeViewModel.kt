package com.dicoding.cinezone.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.cinezone.core.domain.usecase.MovieUseCase
import com.dicoding.cinezone.core.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    movieUseCase: MovieUseCase,
    settingUseCase: SettingUseCase
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val searchResult = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .flatMapLatest { movieUseCase.searchMovie(it) }
        .asLiveData()

    val getTheme = settingUseCase.getThemeSetting().asLiveData()

    val movie = movieUseCase.getMovie().asLiveData()

}