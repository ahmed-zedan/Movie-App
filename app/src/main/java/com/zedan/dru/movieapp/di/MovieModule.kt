package com.zedan.dru.movieapp.di

import com.zedan.dru.movieapp.features.movie.data.GetMoviesListUseCaseImpl
import com.zedan.dru.movieapp.features.movie.domain.usecases.GetMoviesListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MovieModuleBinder {

    @Binds
    @Singleton
    fun bindGetMoviesListUseCase(
        impl: GetMoviesListUseCaseImpl,
    ): GetMoviesListUseCase
}