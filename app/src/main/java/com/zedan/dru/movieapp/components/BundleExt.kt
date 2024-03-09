package com.zedan.dru.movieapp.components

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getParcelable(key, T::class.java)
    else
        this.getParcelable(key)
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): List<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getParcelableArrayList(key, T::class.java)
    else
        this.getParcelableArrayList(key)
}
