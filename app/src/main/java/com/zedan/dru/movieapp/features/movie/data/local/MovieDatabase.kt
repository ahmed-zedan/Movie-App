package com.zedan.dru.movieapp.features.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieList
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListCrossRef
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListCrossRefDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListDao

@Database(
    entities = [Movie::class, MovieList::class, MovieListCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieListDao(): MovieListDao
    abstract fun movieListCrossDao(): MovieListCrossRefDao

    companion object {
        const val DATABASE_NAME = "movies.db"
    }
}
