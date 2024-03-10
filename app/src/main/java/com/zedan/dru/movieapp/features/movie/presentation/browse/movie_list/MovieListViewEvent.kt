package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

sealed class MovieListViewEvent {
    data class Movie(val id: Int): MovieListViewEvent()
}
