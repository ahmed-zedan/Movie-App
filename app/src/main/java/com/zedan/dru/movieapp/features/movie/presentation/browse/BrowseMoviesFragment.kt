package com.zedan.dru.movieapp.features.movie.presentation.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zedan.dru.movieapp.components.view_pager.FragmentViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BrowseMoviesFragment : Fragment() {

    private val viewModel: BrowseMoviesViewModel by viewModels()

    private val renderer by lazy { BrowseMoviesViewRenderer((this)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = renderer.inflate(inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderer.render(viewModel.viewState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        renderer.onDestroy()
    }
}
