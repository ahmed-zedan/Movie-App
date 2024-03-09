package com.zedan.dru.movieapp.features.movie.presentation.browse

import androidx.lifecycle.ViewModel
import com.zedan.dru.movieapp.R
import com.zedan.dru.movieapp.components.resources.TextResource
import com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list.MovieCategory
import com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list.MovieListFragment
import com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list.MovieListFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowseMoviesViewModel @Inject constructor() : ViewModel() {

    val viewState: BrowseMoviesViewState by lazy {
        BrowseMoviesViewState(
            mapOf(
                TextResource.fromStringId(R.string.movie_now_playing) to
                        MovieListFragment.newInstance(MovieListFragmentArgs(MovieCategory.NOW_PLAYING)),
                TextResource.fromStringId(R.string.movie_popular) to
                        MovieListFragment.newInstance(MovieListFragmentArgs(MovieCategory.POPULAR)),
                TextResource.fromStringId(R.string.movie_top_rated) to
                        MovieListFragment.newInstance(MovieListFragmentArgs(MovieCategory.TOP_RATED)),
                TextResource.fromStringId(R.string.movie_upcoming) to
                        MovieListFragment.newInstance(MovieListFragmentArgs(MovieCategory.UP_COMING))
            )
        )
    }
}
