package com.bsrakdg.blogpost.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsrakdg.blogpost.di.keys.MainViewModelKey
import com.bsrakdg.blogpost.ui.viewmodel.MainViewModel
import com.bsrakdg.blogpost.viewmodels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

/* Alternative is provided for test (TestViewModelModule) */
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(vmFactory: MainViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MainViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}
