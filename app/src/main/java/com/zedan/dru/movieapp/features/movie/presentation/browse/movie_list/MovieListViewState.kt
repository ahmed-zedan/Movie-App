package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import com.zedan.dru.movieapp.components.resources.TextResource

data class MovieListViewState(
    val loading: Boolean,
    val showList: Boolean,
    val showEmpty: Boolean,
    val error: TextResource?,
) {
    companion object {
        val EMPTY
            get() = MovieListViewState(
                loading = false,
                showList = false,
                showEmpty = false,
                error = null,
            )
    }
}


fun MovieListViewState.loading() =
    this.copy(
        loading = true,
        showList = false,
        showEmpty = false,
        error = null,
    )

fun MovieListViewState.error(error: TextResource) =
    this.copy(
        loading = false,
        showList = false,
        showEmpty = false,
        error = error,
    )


fun MovieListViewState.empty() =
    this.copy(
        loading = false,
        showList = false,
        showEmpty = true,
        error = null
    )

fun MovieListViewState.data() =
    this.copy(
        loading = false,
        showList = true,
        showEmpty = false,
        error = null,
    )


