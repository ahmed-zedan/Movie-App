package com.zedan.dru.movieapp.features.movie.presentation.browse.movie_list

import android.os.Parcel
import android.os.Parcelable
import com.zedan.dru.movieapp.features.movie.domain.entities.MovieCategory
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith

@Parcelize
data class MovieListFragmentArgs(
    val category: @WriteWith<MovieCategoryParceler>() MovieCategory,
) : Parcelable {
    companion object {
        const val ARGS_KEY = "movie_list_args"
    }
}

object MovieCategoryParceler : Parceler<MovieCategory> {
    override fun create(parcel: Parcel): MovieCategory {
        return MovieCategory.valueOf(parcel.readString()!!)
    }

    override fun MovieCategory.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this.name)
    }
}
