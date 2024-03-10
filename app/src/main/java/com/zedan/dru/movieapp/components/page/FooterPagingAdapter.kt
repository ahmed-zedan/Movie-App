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
            binding.errorView.onRetryClick(retry)
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
