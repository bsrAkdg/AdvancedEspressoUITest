package com.bsrakdg.blogpost.di

import com.bsrakdg.blogpost.api.ApiService
import com.bsrakdg.blogpost.repository.MainRepository
import com.bsrakdg.blogpost.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object RepositoryModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository {
        return MainRepositoryImpl(apiService)
    }
}

