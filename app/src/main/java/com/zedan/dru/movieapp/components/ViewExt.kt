package com.zedan.dru.movieapp.components

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zedan.dru.movieapp.components.utils.EventBlocker

const val CLICK_TIME_SMALL = 400L
const val CLICK_TIME_MEDIUM = 700L

fun interface OnClick : View.OnClickListener

fun View.onClick(preventTime: Long = CLICK_TIME_SMALL, listener: OnClick) {
    val blocker = EventBlocker(preventTime)
    setOnClickListener { view ->
        blocker { listener.onClick(view) }
    }
}


@BindingAdapter("show")
fun View.show(show: Boolean) {
    isVisible = show
}

@BindingAdapter("load")
fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}
