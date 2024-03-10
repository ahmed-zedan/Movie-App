package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zedan.dru.movieapp.features.movie.data.local.MovieLocalSource
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.remote.MovieListPageRemoteSource
import com.zedan.dru.movieapp.features.movie.data.remote.MovieRemoteSource
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListPageController @Inject constructor(
    private val movieRemoteSource: MovieRemoteSource,
    private val movieLocalSource: MovieLocalSource,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun load(request: MovieListRequest): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(MovieListPageRemoteSource.PAGE_SIZE),
            remoteMediator = MovieListPageRemoteSource(request, movieRemoteSource, movieLocalSource)
        ) {
            movieLocalSource.getMovies(request.category.path)
        }.flow
    }
}
