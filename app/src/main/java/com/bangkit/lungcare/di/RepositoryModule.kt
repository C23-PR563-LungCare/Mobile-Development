package com.bangkit.lungcare.di

import com.bangkit.lungcare.data.XrayRepositoryImpl
import com.bangkit.lungcare.domain.repository.XrayRepository
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
    abstract fun provideUserRepository(xrayRepositoryImpl: XrayRepositoryImpl): XrayRepository
}