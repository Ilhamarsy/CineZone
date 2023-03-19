package com.dicoding.cinezone.core.utils

import com.dicoding.cinezone.core.BuildConfig.IMAGE_URL
import com.dicoding.cinezone.core.data.source.remote.response.MovieResponse
import com.dicoding.cinezone.core.domain.model.Movie
import com.dicoding.cinezone.core.data.source.local.entity.MovieEntity

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val tourismList = ArrayList<MovieEntity>()
        input.map {
            val backdropPath = IMAGE_URL + it.backdropPath
            val posterPath = IMAGE_URL + it.posterPath
            val tourism = MovieEntity(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = posterPath,
                backdropPath = backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage as Double,
                isFavorite = false
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) =
        MovieEntity(
            id = input.id,
            overview = input.overview,
            title = input.title,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            isFavorite = input.isFavorite
        )
}