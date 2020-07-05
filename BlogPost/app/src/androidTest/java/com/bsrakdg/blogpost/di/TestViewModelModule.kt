package com.bsrakdg.blogpost.di

import androidx.lifecycle.ViewModelProvider
import com.bsrakdg.blogpost.repository.FakeMainRepositoryImpl
import com.bsrakdg.blogpost.viewmodels.FakeMainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Module
object TestViewModelModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideViewModelFactory(
        mainRepository: FakeMainRepositoryImpl
    ): ViewModelProvider.Factory {
        return FakeMainViewModelFactory(mainRepository)
    }
}