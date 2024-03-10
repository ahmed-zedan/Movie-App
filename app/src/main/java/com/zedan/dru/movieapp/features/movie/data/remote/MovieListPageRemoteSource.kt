package com.zedan.dru.movieapp.features.movie.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zedan.dru.movieapp.features.movie.data.MovieRepository
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import com.zedan.dru.movieapp.features.movie.domain.processor.MovieListPageRefreshProcessor


@OptIn(ExperimentalPagingApi::class)
class MovieListPageRemoteSource(
    private val request: MovieListRequest,
    private val refreshProcessor: MovieListPageRefreshProcessor,
    private val repository: MovieRepository,
) : RemoteMediator<Int, Movie>() {

    override suspend fun initialize(): InitializeAction {
        if (refreshProcessor.refresh(request.category.path)) {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>,
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    repository.getNextPage(request.category.path, FIRST_PAGE)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val items = repository.getMovies(request, loadKey, PAGE_OFFSET)
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: Throwable) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_OFFSET = 1
        const val PAGE_SIZE = 20
    }
}
