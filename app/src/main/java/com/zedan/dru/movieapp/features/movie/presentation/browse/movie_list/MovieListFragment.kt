package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zedan.dru.movieapp.R
import com.zedan.dru.movieapp.components.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val args: MovieListFragmentArgs by lazy { requireArguments().parcelable(ARGS_KEY)!! }

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }


    companion object {
        private const val ARGS_KEY = "movie_list_args"

        fun newInstance(args: MovieListFragmentArgs) = MovieListFragment()
            .apply {
                arguments = Bundle()
                    .apply { putParcelable(ARGS_KEY, args) }
            }
    }
}
