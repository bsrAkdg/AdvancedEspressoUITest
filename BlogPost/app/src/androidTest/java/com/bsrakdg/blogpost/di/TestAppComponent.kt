package com.bsrakdg.blogpost.di

import android.app.Application
import com.bsrakdg.blogpost.api.FakeApiService
import com.bsrakdg.blogpost.repository.FakeMainRepositoryImpl
import com.bsrakdg.blogpost.ui.DetailFragmentTest
import com.bsrakdg.blogpost.ui.ListFragmentErrorTests
import com.bsrakdg.blogpost.ui.ListFragmentIntegrationTests
import com.bsrakdg.blogpost.ui.MainNavigationTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(modules = [
    TestFragmentModule::class,
    TestViewModelModule::class,
    TestAppModule::class
])
interface TestAppComponent : AppComponent{

    // each fragment test class access this fields
    val apiService: FakeApiService

    val mainRepository: FakeMainRepositoryImpl

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(detailFragmentTest: DetailFragmentTest)

    fun inject(listFragmentIntegrationTests: ListFragmentIntegrationTests)

    fun inject(listFragmentErrorTests: ListFragmentErrorTests)

    fun inject(mainNavigationTest: MainNavigationTest)
}