package com.bsrakdg.blogpost.di

import androidx.fragment.app.FragmentFactory
import com.bsrakdg.blogpost.fragments.FakeMainFragmentFactory
import com.bsrakdg.blogpost.util.FakeGlideRequestManager
import com.bsrakdg.blogpost.viewmodels.FakeMainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Module
object TestFragmentModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: FakeMainViewModelFactory,
        glideRequestManager: FakeGlideRequestManager
    ): FragmentFactory {
        return FakeMainFragmentFactory(viewModelFactory, glideRequestManager)
    }
}