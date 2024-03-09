package com.zedan.dru.movieapp.components.resources

import android.content.res.Resources
import androidx.annotation.StringRes


sealed class TextResource {
    abstract fun asString(resource: Resources): String

    companion object {
        fun fromText(text: String): TextResource = StringResource(text)
        fun fromStringId(@StringRes id: Int): TextResource = IdResource(id)
    }
}


private data class StringResource(
    val text: String,
) : TextResource() {
    override fun asString(resource: Resources): String {
        return text
    }
}


private data class IdResource(
    @StringRes val id: Int,
) : TextResource() {
    override fun asString(resource: Resources): String {
        return resource.getString(id)
    }
}
