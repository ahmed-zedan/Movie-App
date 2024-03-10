package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zedan.dru.movieapp.databinding.FragmentMovieListItemRawBinding
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieEntity

class MovieListAdapter : PagingDataAdapter<MovieEntity, MovieListViewHolder>(MovieComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }
}

class MovieListViewHolder private constructor(
    private val binding: FragmentMovieListItemRawBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun newInstance(parent: ViewGroup) = MovieListViewHolder(
            binding = FragmentMovieListItemRawBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun bind(movie: MovieEntity) {
        Glide.with(binding.imageView)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.imageView)
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
