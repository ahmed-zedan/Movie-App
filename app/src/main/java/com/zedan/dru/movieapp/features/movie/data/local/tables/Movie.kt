package com.zedan.dru.movieapp.features.movie.data.local.tables

import androidx.paging.PagingSource
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String?,
    @ColumnInfo(name = "original_title") val originalTitle: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: Float?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "video") val video: Boolean?,
    @ColumnInfo(name = "vote_average") val voteAverage: Float?,
    @ColumnInfo(name = "vote_count") val voteCount: Int?,
)


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movie_list_cross_ref, movie WHERE id=movie_id AND category=:category ORDER BY position ASC")
    @RewriteQueriesToDropUnusedColumns
    fun getAll(category: String): PagingSource<Int, Movie>

    @Query("SELECT id FROM movie")
    suspend fun getIds(): List<Int>

    @Query("DELETE FROM movie WHERE id=:id")
    suspend fun delete(id: Int)
}
