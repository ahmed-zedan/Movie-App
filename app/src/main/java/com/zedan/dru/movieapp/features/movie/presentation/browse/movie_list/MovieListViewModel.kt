package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import com.zedan.dru.movieapp.components.exceptions.ErrorMapper
import com.zedan.dru.movieapp.components.page.FooterPagingAdapter
import com.zedan.dru.movieapp.components.page.asMergedLoadStates
import com.zedan.dru.movieapp.features.movie.data.invoke
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity
import com.zedan.dru.movieapp.features.movie.domain.usecases.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getMoviesUseCase: GetMoviesListUseCase,
    private val errorMapper: ErrorMapper,
) : ViewModel() {
    private val args: MovieListFragmentArgs
        get() = savedStateHandle[MovieListFragmentArgs.ARGS_KEY]!!

    private val movies = getMoviesUseCase.invoke(args.category)
        .cachedIn(viewModelScope)

    private val _viewState = MutableStateFlow(MovieListViewState.EMPTY)
    val viewState get() = _viewState.asStateFlow()

    private val _eventState = Channel<MovieListViewEvent>()
    val eventState get() = _eventState.receiveAsFlow()

    private val moviesAdapter by lazy { MovieListAdapter(MovieListItemListener()) }
    private val footerAdapter by lazy { FooterPagingAdapter(moviesAdapter::retry) }
    val adapter by lazy { moviesAdapter.withLoadStateFooter(footerAdapter) }
    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.getItemViewType(position) == FooterPagingAdapter.STATE_BUSY) 2 else 1
        }
    }


    init {
        consumeMovies()
        addLoadMoviesState()
        consumeMoviesState()
    }

    fun onRetry() {
        moviesAdapter.retry()
    }

    private fun addLoadMoviesState() {
        moviesAdapter.addLoadStateListener { loadStates ->
            footerAdapter.loadState = loadStates.append
        }
    }

    private fun consumeMovies() {
        viewModelScope.launch {
            movies.collectLatest { moviesAdapter.submitData(it) }
        }
    }

    private fun consumeMoviesState() {
        viewModelScope.launch {
            moviesAdapter.loadStateFlow
                .asMergedLoadStates()
                .map { it.refresh }
                .distinctUntilChanged()
                .collect { state ->
                    _viewState.getAndUpdate {
                        when (state) {
                            is LoadState.Loading -> it.loading()
                            is LoadState.NotLoading -> it.data()
                            is LoadState.Error -> it.error(errorMapper.map(state.error))
                        }
                    }
                }
        }
    }

    private inner class MovieListItemListener : MovieListViewHolder.Listener {
        override fun onMovieClick(movie: MovieEntity) {
            viewModelScope.launch {
                _eventState.send(MovieListViewEvent.Movie(movie.id))
            }
        }
    }
}
