package com.bangkit.lungcare.di

import com.bangkit.lungcare.BuildConfig
import com.bangkit.lungcare.data.source.local.datastore.UserPreferencesImpl
import com.bangkit.lungcare.data.source.remote.RemoteDataSource
import com.bangkit.lungcare.data.source.remote.retrofit.XrayApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): XrayApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(XrayApiService::class.java)
    }
}