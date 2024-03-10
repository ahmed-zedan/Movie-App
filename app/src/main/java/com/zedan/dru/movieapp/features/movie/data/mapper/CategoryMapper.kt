package com.zedan.dru.movieapp.features.movie.data.mapper

import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListCategoryPath
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieCategory

fun MovieCategory.toRemote(): MovieListCategoryPath {
    return when (this) {
        MovieCategory.NOW_PLAYING -> MovieListCategoryPath.NOW_PLAYING
        MovieCategory.POPULAR -> MovieListCategoryPath.POPULAR
        MovieCategory.TOP_RATED -> MovieListCategoryPath.TOP_RATED
        MovieCategory.UP_COMING -> MovieListCategoryPath.UP_COMING
    }
}