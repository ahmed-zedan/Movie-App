package com.zedan.dru.movieapp.features.movie.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.paging.PagingSource
import com.zedan.dru.movieapp.core.Ports
import com.zedan.dru.movieapp.features.movie.data.local.tables.Movie
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieList
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListCrossRef
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListCrossRefDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val movieDao: MovieDao,
    private val movieListDao: MovieListDao,
    private val movieListCrossRefDao: MovieListCrossRefDao,
    private val ports: Ports,
) {

    private val sharedPref by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    suspend fun getMovieList(category: String): MovieList? {
        return movieListDao.getByCategory(category)
    }

    suspend fun add(item: MovieList) {
        movieListDao.insert(item)
    }

    suspend fun add(category: String, movies: List<Movie>?) {
        if (movies.isNullOrEmpty()) {
            return
        }

        movieDao.insert(movies)
        val nextIndex = movieListCrossRefDao.getNextIndexInList(category)
        movieListCrossRefDao.insert(movies.mapIndexed { index, movie ->
            MovieListCrossRef(
                category,
                movie.id,
                nextIndex + index
            )
        })
    }

    suspend fun deleteMovieRef(category: String) {
        withContext(ports.io()) {
            val deleteRefsCategory = async { movieListCrossRefDao.delete(category) }
            val deleteUnusedMovie = async {
                val ids = movieDao.getIds()
                for (id in ids) {
                    val refs = movieListCrossRefDao.getByMovieId(id)
                    if (refs.isEmpty()) {
                        movieDao.delete(id)
                    }
                }
            }

            listOf(deleteRefsCategory, deleteUnusedMovie).awaitAll()
        }
    }

    fun getMovies(category: String): PagingSource<Int, Movie> {
        return movieDao.getAll(category)
    }

    fun updateLastSyncTime(category: String, time: Long) {
        sharedPref.edit {
            putLong("${category}_$LAST_SYNC_TIME", time)
        }
    }

    fun getLastSyncTime(category: String): Long {
        return sharedPref.getLong("${category}_$LAST_SYNC_TIME", 0)
    }


    companion object {
        private const val SHARED_PREF_NAME = "movie_shared_pref"
        private const val LAST_SYNC_TIME = "movie_last_sync_time"
    }
}
