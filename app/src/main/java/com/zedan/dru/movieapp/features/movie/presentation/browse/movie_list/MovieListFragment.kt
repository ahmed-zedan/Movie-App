package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zedan.dru.movieapp.R
import com.zedan.dru.movieapp.components.parcelable
import com.zedan.dru.movieapp.databinding.FragmentMovieListBinding
import com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list.MovieListFragmentArgs.Companion.ARGS_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()

    private val eventProcessor by lazy { MovieListEventProcessor(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.eventState.collect { event ->
                eventProcessor.process(event)
            }
        }
    }


    companion object {
        fun newInstance(args: MovieListFragmentArgs) = MovieListFragment()
            .apply {
                arguments = Bundle()
                    .apply { putParcelable(ARGS_KEY, args) }
            }
    }
}
