package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.PagingSource
import com.zedan.dru.movieapp.features.movie.data.local.MovieLocalSource
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieList
import com.zedan.dru.movieapp.features.movie.data.mapper.toEntity
import com.zedan.dru.movieapp.features.movie.data.mapper.toLocalModel
import com.zedan.dru.movieapp.features.movie.data.remote.MovieRemoteSource
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localSource: MovieLocalSource,
    private val remoteSource: MovieRemoteSource,
) {

    suspend fun getMovies(request: MovieListRequest, page: Int, offset: Int): List<MovieEntity> {
        val category = request.category.path
        val response = remoteSource.getMovieList(category, page)
        if (response.movies.isNullOrEmpty())
            return emptyList()

        localSource.add(
            MovieList(
                category,
                response.page?.takeIf { it < (response.totalPages ?: it) }?.plus(offset)
            )
        )

        localSource.add(category, response.movies.map { it.toLocalModel() })

        return response.movies.map { it.toEntity() }
    }

    fun getCacheMovies(category: String): PagingSource<Int, Movie> {
        return localSource.getMovies(category)
    }

    suspend fun getMovie(id: Int): MovieEntity {
        return remoteSource.getMovie(id)
            .toEntity()
    }

    suspend fun getNextPage(category: String, default: Int): Int? {
        val movies = localSource.getMovieList(category)
        if (movies != null && movies.nextPage == null)
            return null

        return movies?.nextPage ?: default
    }

    suspend fun deleteMovies(category: String) {
        localSource.deleteMovieRef(category)
    }

    fun getLastSyncTime(category: String): Long {
        return localSource.getLastSyncTime(category)
    }

    fun updateLastSyncTime(category: String, timeMillis: Long) {
        localSource.updateLastSyncTime(category, timeMillis)
    }
}
