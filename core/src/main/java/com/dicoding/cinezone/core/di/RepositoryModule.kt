package com.dicoding.cinezone.core.di

import com.dicoding.cinezone.core.data.MovieRepository
import com.dicoding.cinezone.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(tourismRepository: MovieRepository): IMovieRepository

}