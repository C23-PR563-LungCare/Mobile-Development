package com.bangkit.lungcare.di

import com.bangkit.lungcare.domain.usecase.article.ArticleInteractor
import com.bangkit.lungcare.domain.usecase.article.ArticleUseCase
import com.bangkit.lungcare.domain.usecase.user.UserInteractor
import com.bangkit.lungcare.domain.usecase.user.UserUseCase
import com.bangkit.lungcare.domain.usecase.xray.XrayInteractor
import com.bangkit.lungcare.domain.usecase.xray.XrayUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideXrayUseCase(xrayInteractor: XrayInteractor): XrayUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideArticleUseCase(articleInteractor: ArticleInteractor): ArticleUseCase
}