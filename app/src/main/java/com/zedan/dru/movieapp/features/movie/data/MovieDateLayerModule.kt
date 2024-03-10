package com.zedan.dru.movieapp.features.movie.data

import android.content.Context
import androidx.room.Room
import com.zedan.dru.movieapp.features.movie.data.local.MovieDatabase
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListCrossRefDao
import com.zedan.dru.movieapp.features.movie.data.local.tables.MovieListDao
import com.zedan.dru.movieapp.features.movie.data.remote.MovieRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDateLayerModuleProvider {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao =
        database.movieDao()

    @Provides
    @Singleton
    fun provideMovieListDao(database: MovieDatabase): MovieListDao =
        database.movieListDao()

    @Provides
    @Singleton
    fun provideMovieListCrossRefDao(database: MovieDatabase): MovieListCrossRefDao =
        database.movieListCrossDao()

    @Provides
    @Singleton
    fun provideMovieRemoteSource(
        retrofit: Retrofit,
    ): MovieRemoteSource =
        retrofit.create(MovieRemoteSource::class.java)
}
