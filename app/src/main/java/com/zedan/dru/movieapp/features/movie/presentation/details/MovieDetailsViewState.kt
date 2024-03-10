package com.zedan.dru.movieapp.features.movie.presentation.details

import com.zedan.dru.movieapp.components.resources.TextResource
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity

data class MovieDetailsViewState(
    val loading: Boolean,
    val movie: MovieEntity?,
    val error: TextResource?,
) {
    companion object {
        val EMPTY get() = MovieDetailsViewState(loading = false, movie = null, error = null)
    }
}
