package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import com.zedan.dru.movieapp.components.exceptions.ErrorMapper
import com.zedan.dru.movieapp.components.page.FooterPagingAdapter
import com.zedan.dru.movieapp.components.page.asMergedLoadStates
import com.zedan.dru.movieapp.components.utils.Event
import com.zedan.dru.movieapp.features.movie.data.invoke
import com.zedan.dru.movieapp.features.movie.domain.usecases.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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

    private val moviesAdapter by lazy { MovieListAdapter() }
    private val footerAdapter by lazy { FooterPagingAdapter(moviesAdapter::retry) }
    val adapter by lazy {
        moviesAdapter.withLoadStateFooter(footerAdapter)
    }

    private val _loading = MutableStateFlow(true)
    val loading get() = _loading.asStateFlow()

    private val _showList = MutableStateFlow(false)
    val showList get() = _showList.asStateFlow()

    private val _moveToPosition = MutableStateFlow(Event<Int>(RecyclerView.NO_POSITION))
    val moveToPosition get() = _moveToPosition.asStateFlow()


    init {
        consumeMovies()
        addLoadMoviesState()
        consumeMoviesState()
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
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    _loading.value = false
                    _showList.value = true
                    _moveToPosition.value = Event(0)
                }
        }
    }
}
