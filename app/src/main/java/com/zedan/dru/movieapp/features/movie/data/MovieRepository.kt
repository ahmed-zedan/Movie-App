package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.PagingData
import androidx.paging.map
import com.zedan.dru.movieapp.features.movie.data.local.MovieLocalSource
import com.zedan.dru.movieapp.features.movie.data.mapper.toEntity
import com.zedan.dru.movieapp.features.movie.data.mapper.toRemote
import com.zedan.dru.movieapp.features.movie.data.remote.MovieRemoteSource
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieCategory
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieListPageController: MovieListPageController,
    private val localSource: MovieLocalSource,
    private val remoteSource: MovieRemoteSource,
) {
    fun getMovies(category: MovieCategory): Flow<PagingData<MovieEntity>> {
        Timber.wtf("Get Movies $category")
        return movieListPageController.load(MovieListRequest(category.toRemote()))
            .map { page -> page.map { m -> m.toEntity() } }
    }

    suspend fun getMovie(id: Int): MovieEntity {
        return remoteSource.getMovie(id)
            .toEntity()
    }
}
