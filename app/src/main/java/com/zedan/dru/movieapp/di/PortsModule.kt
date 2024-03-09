package com.zedan.dru.movieapp.di

import com.zedan.dru.movieapp.core.CoroutinePorts
import com.zedan.dru.movieapp.core.Ports
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PortsModuleProvider {

    @Provides
    @Singleton
    fun providePorts(): Ports {
        return CoroutinePorts()
    }
}
