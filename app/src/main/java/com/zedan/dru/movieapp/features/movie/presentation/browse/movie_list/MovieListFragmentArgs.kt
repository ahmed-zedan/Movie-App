package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListFragmentArgs(
    val category: MovieCategory,
) : Parcelable


@Parcelize
enum class MovieCategory : Parcelable {
    NOW_PLAYING,
    POPULAR,
    TOP_RATED,
    UP_COMING
}
