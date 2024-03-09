package com.zedan.dru.movieapp.features.movie.presentation.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.zedan.dru.movieapp.components.view_pager.FragmentViewPagerAdapter
import com.zedan.dru.movieapp.databinding.FragmentBrowseMoviesBinding

class BrowseMoviesViewRenderer(
    private val categoryAdapters: FragmentViewPagerAdapter,
) {
    private var _binding: FragmentBrowseMoviesBinding? = null
    private val binding get() = _binding!!

    private val resources get() = binding.root.resources

    fun inflate(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentBrowseMoviesBinding.inflate(inflater, container, false)
        inflateViewBinder()
        return binding.root
    }

    private fun inflateViewBinder() {
        binding.moviesCategoryViewPager.isUserInputEnabled = false
        binding.moviesCategoryViewPager.adapter = categoryAdapters
    }

    fun render(state: BrowseMoviesViewState) {
        categoryAdapters.submit(state.categories.values)
        TabLayoutMediator(
            binding.moviesCategoryTabLayout,
            binding.moviesCategoryViewPager
        ) { tab, index -> tab.text = state.categories.keys.elementAt(index).asString(resources) }
            .attach()
    }


    fun onDestroy() {
        _binding = null
    }
}

