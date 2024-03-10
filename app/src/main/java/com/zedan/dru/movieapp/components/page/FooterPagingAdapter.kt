package com.zedan.dru.movieapp.components.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zedan.dru.movieapp.R
import com.zedan.dru.movieapp.components.resources.TextResource
import com.zedan.dru.movieapp.databinding.FooterPagingRecyclerBinding


class FooterPagingAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<FooterPagingAdapter.FooterPagingViewHolder>() {

    companion object{
        const val STATE_NOT_LOADING = -4
        const val STATE_BUSY = -1
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return if (loadState is LoadState.NotLoading) STATE_NOT_LOADING else STATE_BUSY
    }

    override fun onBindViewHolder(holder: FooterPagingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): FooterPagingViewHolder {
        return FooterPagingViewHolder.create(parent, retry)
    }


    class FooterPagingViewHolder(
        private val binding: FooterPagingRecyclerBinding,
        retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.errorView.setOnRetryClick(retry)
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorView.setError(TextResource.fromText(loadState.error.message ?: ""))
            }
            binding.loadingView.isVisible = loadState is LoadState.Loading
            binding.errorView.isVisible = loadState is LoadState.Error
        }


        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): FooterPagingViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.footer_paging_recycler, parent, false)
                val binding = FooterPagingRecyclerBinding.bind(view)
                return FooterPagingViewHolder(binding, retry)
            }
        }
    }
}
