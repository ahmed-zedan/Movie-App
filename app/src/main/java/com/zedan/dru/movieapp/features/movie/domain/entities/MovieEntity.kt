package com.zedan.dru.movieapp.features.movie.domain.entities


data class MovieEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val id: Int,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
)
