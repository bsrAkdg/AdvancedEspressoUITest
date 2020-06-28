package com.bsrakdg.blogpost.di

import androidx.fragment.app.FragmentFactory
import com.bsrakdg.blogpost.fragments.MainFragmentFactory
import com.bsrakdg.blogpost.util.GlideManager
import com.bsrakdg.blogpost.viewmodels.MainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object FragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: MainViewModelFactory,
        glideManager: GlideManager
    ): FragmentFactory {
        return MainFragmentFactory(viewModelFactory, glideManager)
    }

}