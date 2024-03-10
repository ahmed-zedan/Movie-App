package com.zedan.dru.movieapp.components.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zedan.dru.movieapp.R

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val root: View

    init {
        root =
            LayoutInflater.from(context).inflate(R.layout.empty_view, this, true)
    }

}
