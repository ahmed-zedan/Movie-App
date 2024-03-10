package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.PagingData
import androidx.paging.map
import com.zedan.dru.movieapp.features.movie.data.mapper.toEntity
import com.zedan.dru.movieapp.features.movie.data.mapper.toRemote
import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListRequest
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieCategory
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity
import com.zedan.dru.movieapp.features.movie.domain.usecases.GetMoviesListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesListUseCaseImpl @Inject constructor(
    internal val movieListPageController: MovieListPageController,
) : GetMoviesListUseCase

fun GetMoviesListUseCase.invoke(category: MovieCategory): Flow<PagingData<MovieEntity>> {
    val pageController = (this as GetMoviesListUseCaseImpl).movieListPageController
    return pageController.load(MovieListRequest(category.toRemote()))
        .map { page -> page.map { m -> m.toEntity() } }
}
