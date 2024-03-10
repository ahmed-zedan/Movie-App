package com.zedan.dru.movieapp.features.movie.data.local.tables

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "movie_list")
data class MovieList(
    @PrimaryKey
    val category: String,
    @ColumnInfo("next_page")
    val nextPage: Int?
)


@Dao
interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MovieList)

    @Query("SELECT * FROM movie_list WHERE category=:category")
    suspend fun getByCategory(category: String): MovieList?

    @Query("DELETE FROM movie_list WHERE category=:category")
    suspend fun delete(category: String)
}
