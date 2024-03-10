package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.remote.MovieListPageRemoteSource
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import com.zedan.dru.movieapp.features.movie.domain.processor.MovieListPageRefreshProcessor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListPageController @Inject constructor(
    private val refreshProcessor: MovieListPageRefreshProcessor,
    private val repository: MovieRepository,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun load(request: MovieListRequest): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(MovieListPageRemoteSource.PAGE_SIZE),
            remoteMediator = MovieListPageRemoteSource(request, refreshProcessor, repository)
        ) {
            repository.getCacheMovies(request.category.path)
        }.flow
    }
}
