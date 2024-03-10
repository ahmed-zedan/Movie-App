package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zedan.dru.movieapp.R
import com.zedan.dru.movieapp.features.movie.presentation.details.MovieDetailsFragmentArgs

class MovieListEventProcessor(val fragment: Fragment) {
    fun process(event: MovieListViewEvent) {
        when (event) {
            is MovieListViewEvent.Movie ->
                navigateToDetail(event.id)
        }
    }

    private fun navigateToDetail(id: Int) {
        fragment.findNavController()
            .navigate(
                R.id.movieDetailsFragment,
                MovieDetailsFragmentArgs.Builder(id).build().toBundle()
            )
    }
}
