package com.lina.data.di

import com.lina.data.impl.RepositoryImpl
import com.lina.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindRepository(repository: RepositoryImpl): Repository

}