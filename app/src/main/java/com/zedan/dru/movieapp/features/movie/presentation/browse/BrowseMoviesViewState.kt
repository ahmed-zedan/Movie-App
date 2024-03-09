package com.zedan.dru.movieapp.features.movie.presentation.browse

import androidx.fragment.app.Fragment
import com.zedan.dru.movieapp.components.resources.TextResource

data class BrowseMoviesViewState(
    val categories: Map<TextResource, Fragment>
)
