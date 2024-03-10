package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import com.zedan.dru.movieapp.components.resources.TextResource
import com.zedan.dru.movieapp.components.utils.Event

data class MovieListViewState(
    val loading: Boolean = true,
    val showList: Boolean,
    val error: TextResource?,
    val position: Event<Int>? = null,
) {
    companion object {
        val EMPTY
            get() = MovieListViewState(
                loading = false,
                showList = false,
                error = null,
            )
    }
}


fun MovieListViewState.loading() =
    this.copy(
        loading = true,
        showList = false,
        error = null,
    )

fun MovieListViewState.error(error: TextResource) =
    this.copy(
        loading = false,
        showList = false,
        error = error,
    )


fun MovieListViewState.data() =
    this.copy(
        loading = false,
        showList = true,
        error = null,
        position = Event(0)
    )
