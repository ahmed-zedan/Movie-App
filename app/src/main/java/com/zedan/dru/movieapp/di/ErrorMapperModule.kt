package com.zedan.dru.movieapp.di

import com.zedan.dru.movieapp.components.exceptions.ErrorMapper
import com.zedan.dru.movieapp.components.exceptions.ErrorMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ErrorMapperModuleBinder {


    @Binds
    @Singleton
    fun bindErrorMapper(impl: ErrorMapperImpl): ErrorMapper
}