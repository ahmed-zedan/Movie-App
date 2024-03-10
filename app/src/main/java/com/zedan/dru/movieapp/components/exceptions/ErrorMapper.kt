package com.zedan.dru.movieapp.components.exceptions

import com.zedan.dru.movieapp.components.resources.TextResource
import javax.inject.Inject

fun interface ErrorMapper {
    fun map(t: Throwable): TextResource
}


class ErrorMapperImpl @Inject constructor(): ErrorMapper {
    override fun map(t: Throwable): TextResource {
        return TextResource.fromText(t.message ?: "Error")
    }
}