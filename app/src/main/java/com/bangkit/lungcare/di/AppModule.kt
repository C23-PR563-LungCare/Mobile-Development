package com.bangkit.lungcare.di

import com.bangkit.lungcare.domain.usecase.XrayInteractor
import com.bangkit.lungcare.domain.usecase.XrayUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideUserUseCase(xrayInteractor: XrayInteractor): XrayUseCase
}