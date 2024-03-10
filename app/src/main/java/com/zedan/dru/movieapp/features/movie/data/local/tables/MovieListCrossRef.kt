package com.zedan.dru.movieapp.features.movie.data.local.tables

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Entity(tableName = "movie_list_cross_ref", primaryKeys = ["category", "movie_id"])
data class MovieListCrossRef(
    val category: String,
    @ColumnInfo("movie_id")
    val movieId: Int,
    val position: Int
)


@Dao
interface MovieListCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<MovieListCrossRef>)
    @Query("SELECT * FROM movie_list_cross_ref WHERE movie_id=:id")
    suspend fun getByMovieId(id: Int): List<MovieListCrossRef>
    @Query("DELETE FROM movie_list_cross_ref WHERE category=:category")
    suspend fun delete(category: String)

    @Query("SELECT MAX(position) + 1 FROM movie_list_cross_ref WHERE category=:category")
    suspend fun getNextIndexInList(category: String): Int
}

