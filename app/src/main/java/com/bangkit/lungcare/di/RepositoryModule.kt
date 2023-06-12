package com.bangkit.lungcare.di

import com.bangkit.lungcare.data.repository.ArticleRepositoryImpl
import com.bangkit.lungcare.data.repository.UserRepositoryImpl
import com.bangkit.lungcare.data.repository.XrayRepositoryImpl
import com.bangkit.lungcare.domain.repository.ArticleRepository
import com.bangkit.lungcare.domain.repository.UserRepository
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
    abstract fun provideXrayRepository(xrayRepositoryImpl: XrayRepositoryImpl): XrayRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}