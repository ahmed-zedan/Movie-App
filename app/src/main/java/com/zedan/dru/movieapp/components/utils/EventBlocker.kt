package com.zedan.dru.movieapp.components.utils

import android.os.SystemClock

class EventBlocker(private val preventTime: Long) {

    private var lastEventTime: Long = 0

    operator fun invoke(block: () -> Unit) {
        if (SystemClock.elapsedRealtime() - lastEventTime < preventTime) {
            return
        }
        lastEventTime = SystemClock.elapsedRealtime()
        block.invoke()
    }
}
