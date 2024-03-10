package com.zedan.dru.movieapp.features.movie.data

import androidx.paging.PagingData
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieCategory
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity
import com.zedan.dru.movieapp.features.movie.domain.usecases.GetMoviesListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesListUseCaseImpl @Inject constructor(
    internal val repository: MovieRepository,
) : GetMoviesListUseCase

fun GetMoviesListUseCase.invoke(category: MovieCategory): Flow<PagingData<MovieEntity>> {
    val repository = (this as GetMoviesListUseCaseImpl).repository
    return repository.getMovies(category)
}