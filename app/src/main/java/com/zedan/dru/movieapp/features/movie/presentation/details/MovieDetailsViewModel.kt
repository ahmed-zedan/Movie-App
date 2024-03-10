package com.zedan.dru.movieapp.features.movie.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zedan.dru.movieapp.components.exceptions.ErrorMapper
import com.zedan.dru.movieapp.core.Ports
import com.zedan.dru.movieapp.features.movie.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val ports: Ports,
    private val errorMapper: ErrorMapper,
) : ViewModel() {
    private val movieId: Int get() = savedStateHandle[MOVIE_ID_KEY]!!

    private val _viewState = MutableStateFlow(MovieDetailsViewState.EMPTY)
    val viewState get() = _viewState.asStateFlow()

    init {
        getMovie()
    }

    private var getMovieJob: Job? = null
    fun getMovie() {
        getMovieJob?.cancel()
        getMovieJob = viewModelScope.launch(ports.io()) {
            try {
                _viewState.getAndUpdate { state ->
                    state.copy(
                        loading = true,
                        movie = null,
                        error = null
                    )
                }

                val movie = movieRepository.getMovie(movieId)

                _viewState.getAndUpdate { state ->
                    state.copy(
                        loading = false,
                        movie = movie,
                    )
                }
            } catch (t: Throwable) {
                _viewState.getAndUpdate { state ->
                    state.copy(
                        loading = false,
                        error = errorMapper.map(t),
                    )
                }
            }
        }
    }

    companion object {
        private const val MOVIE_ID_KEY = "movie_id"
    }
}
