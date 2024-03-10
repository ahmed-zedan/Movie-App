package com.zedan.dru.movieapp.features.movie.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zedan.dru.movieapp.features.movie.data.local.MovieLocalSource
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieList
import com.zedan.dru.movieapp.features.movie.data.mapper.toLocalModel
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class MovieListPageRemoteSource(
    private val request: MovieListRequest,
    private val movieRemoteSource: MovieRemoteSource,
    private val movieLocalSource: MovieLocalSource,
) : RemoteMediator<Int, Movie>() {

    override suspend fun initialize(): InitializeAction {
        val lastSyncTime = movieLocalSource.getLastSyncTime(request.category.path)
        if ((System.currentTimeMillis() - lastSyncTime) >= FOUR_HOURS_IN_MILLIS) {
            movieLocalSource.deleteMovieRef(request.category.path)
            movieLocalSource.updateLastSyncTime(request.category.path, System.currentTimeMillis())
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }
        Timber.wtf("initialize invoked")
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>,
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val category = request.category.path
            Timber.wtf("Load type $loadType")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> {
                    Timber.wtf("prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val list = movieLocalSource.getMovieList(category)
                    Timber.wtf("append $list")
                    if (list != null && list.nextPage == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    list?.nextPage ?: FIRST_PAGE
                }
            }

            val response = movieRemoteSource.getMovieList(
                category,
                loadKey
            )


            val items = response.movies ?: emptyList()

            if (loadType == LoadType.REFRESH) {
                movieLocalSource.deleteMovieRef(category)
            }

            movieLocalSource.add(
                MovieList(
                    category,
                    response.page?.takeIf { it < (response.totalPages ?: it) }?.plus(PAGE_OFFSET)
                )
            )

            movieLocalSource.add(category, response.movies?.map { it.toLocalModel() })

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: Throwable) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_OFFSET = 1
        const val PAGE_SIZE = 20
        private const val FOUR_HOURS_IN_MILLIS = 4 * 60 * 60 * 1000
    }
}
