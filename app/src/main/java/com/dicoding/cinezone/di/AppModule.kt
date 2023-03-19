package com.dicoding.cinezone.di

import com.dicoding.cinezone.core.domain.usecase.MovieInteractor
import com.dicoding.cinezone.core.domain.usecase.MovieUseCase
import com.dicoding.cinezone.core.domain.usecase.SettingInteractor
import com.dicoding.cinezone.core.domain.usecase.SettingUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    @Singleton
    abstract fun provideSettingUseCase(settingInteractor: SettingInteractor): SettingUseCase

}