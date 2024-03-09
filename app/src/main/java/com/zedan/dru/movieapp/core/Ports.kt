package com.zedan.dru.movieapp.core


import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface Ports {
    fun main(): CoroutineContext
    fun io(): CoroutineContext
    fun default(): CoroutineContext
}


class CoroutinePorts : Ports {
    override fun main(): CoroutineContext = Dispatchers.Main
    override fun io(): CoroutineContext = Dispatchers.IO
    override fun default(): CoroutineContext = Dispatchers.Default
}
