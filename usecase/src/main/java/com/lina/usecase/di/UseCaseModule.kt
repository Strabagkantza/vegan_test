package com.lina.usecase.di

import com.lina.usecase.UseCase
import com.lina.usecase.impl.UseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    internal abstract fun bindUseCase(useCaseImpl: UseCaseImpl): UseCase
}