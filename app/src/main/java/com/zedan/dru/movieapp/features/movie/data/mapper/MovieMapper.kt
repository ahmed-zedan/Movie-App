package com.zedan.dru.movieapp.features.movie.data.mapper

import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListModelResponse
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity


fun MovieListModelResponse.toLocalModel() = Movie(
    id = this.id,
    adult = this.adult,
    backdropPath = this.backdropPath,
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount
)

fun Movie.toEntity() = MovieEntity(
    adult = this.adult,
    backdropPath = this.backdropPath,
    id = this.id,
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount
)
