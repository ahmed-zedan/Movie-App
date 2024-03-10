package com.zedan.dru.movieapp.features.movie.domain.processor

import com.zedan.dru.movieapp.features.movie.data.MovieRepository
import javax.inject.Inject


class MovieListPageRefreshProcessor @Inject constructor(
    private val repository: MovieRepository,
) {

    suspend fun refresh(category: String): Boolean {
        val lastSyncTime = repository.getLastSyncTime(category)
        if ((System.currentTimeMillis() - lastSyncTime) >= FOUR_HOURS_IN_MILLIS) {
            repository.deleteMovies(category)
            repository.updateLastSyncTime(category, System.currentTimeMillis())
            return true
        }

        return false
    }

    companion object {
        private const val FOUR_HOURS_IN_MILLIS = 4 * 60 * 60 * 1000
    }
}