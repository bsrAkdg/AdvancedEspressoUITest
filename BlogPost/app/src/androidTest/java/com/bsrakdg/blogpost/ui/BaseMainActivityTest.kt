package com.bsrakdg.blogpost.ui

import com.bsrakdg.blogpost.TestBaseApplication
import com.bsrakdg.blogpost.api.FakeApiService
import com.bsrakdg.blogpost.di.TestAppComponent
import com.bsrakdg.blogpost.repository.FakeMainRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainActivityTest {

    abstract fun injectTest(application: TestBaseApplication)

    fun configureFakeApiService(
        blogDataSource: String? = null, // file name
        categoriesDataSource: String? = null,
        networkDelay: Long? = null,
        application: TestBaseApplication
    ): FakeApiService {
        val apiService = (application.appComponent as TestAppComponent).apiService
        blogDataSource?.let { apiService.blogPostJsonFileName = it }
        categoriesDataSource?.let { apiService.categoriesJsonFileName = it }
        networkDelay?.let { apiService.networkDelay = it }
        return apiService
    }

    fun configureFakeRepository(
        apiService: FakeApiService,
        application: TestBaseApplication
    ): FakeMainRepositoryImpl {
        val mainRepository = (application.appComponent as TestAppComponent).mainRepository
        mainRepository.apiService = apiService
        return mainRepository
    }
}