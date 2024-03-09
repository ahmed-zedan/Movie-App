package com.zedan.dru.movieapp.components.exceptions

import com.zedan.dru.movieapp.components.resources.TextResource

fun interface ErrorMapper {
    fun map(t: Throwable): TextResource
}
