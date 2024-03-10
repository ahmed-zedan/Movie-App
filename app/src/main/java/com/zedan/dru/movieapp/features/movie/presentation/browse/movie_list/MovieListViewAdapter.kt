package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zedan.dru.movieapp.components.utils.Event
import timber.log.Timber

@BindingAdapter("move_to")
fun RecyclerView.moveToPosition(position: Event<Int>) {
    position.getContentIfNotHandled()
        ?.takeIf { it > RecyclerView.NO_POSITION }
        ?.let {
            Timber.wtf("Scroll to position $it")
            scrollToPosition(it)
        }
}
