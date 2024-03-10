package com.zedan.dru.movieapp.components.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zedan.dru.movieapp.components.onClick
import com.zedan.dru.movieapp.components.resources.TextResource
import com.zedan.dru.movieapp.databinding.ErrorViewBinding


class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val root = ErrorViewBinding.inflate(LayoutInflater.from(context), this)


    fun onRetryClick(retry: () -> Unit) {
        this.root.retryBtn.onClick { retry() }
    }

    fun setError(message: TextResource?) {
        this.root.errorTxt.text = message?.asString(resources)
    }
}
