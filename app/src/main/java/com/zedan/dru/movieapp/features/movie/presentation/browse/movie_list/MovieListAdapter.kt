package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zedan.dru.movieapp.components.load
import com.zedan.dru.movieapp.components.onClick
import com.zedan.dru.movieapp.databinding.FragmentMovieListItemRawBinding
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity

class MovieListAdapter(
    private val listener: MovieListViewHolder.Listener,
) : PagingDataAdapter<MovieEntity, MovieListViewHolder>(MovieComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder.newInstance(parent, listener)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }
}

class MovieListViewHolder private constructor(
    private val binding: FragmentMovieListItemRawBinding,
    private val listener: Listener,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup, listener: Listener) = MovieListViewHolder(
            binding = FragmentMovieListItemRawBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener = listener
        )
    }

    fun bind(movie: MovieEntity) {
        binding.imageView.load(movie.posterPath)
        binding.root.onClick {
            listener.onMovieClick(movie)
        }
    }

    fun interface Listener {
        fun onMovieClick(movie: MovieEntity)
    }
}

object MovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }
}
