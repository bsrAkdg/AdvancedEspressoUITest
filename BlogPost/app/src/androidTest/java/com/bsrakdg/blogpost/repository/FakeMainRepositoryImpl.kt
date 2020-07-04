package com.bsrakdg.blogpost.repository

import com.bsrakdg.blogpost.api.FakeApiService
import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.models.Category
import com.bsrakdg.blogpost.ui.viewmodel.state.MainViewState
import com.bsrakdg.blogpost.util.ApiResponseHandler
import com.bsrakdg.blogpost.util.DataState
import com.bsrakdg.blogpost.util.StateEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMainRepositoryImpl
@Inject
constructor() : MainRepository {

    lateinit var apiService: FakeApiService

    @Throws(UninitializedPropertyAccessException::class)
    override fun getBlogs(
        stateEvent: StateEvent,
        category: String
    ): Flow<DataState<MainViewState>> {
        thrownExceptionIfApiServiceNotInitialized()

        return flow {
            val response = safeApiCall(Dispatchers.IO) { apiService.getBlogPosts(category) }

            emit(
                object : ApiResponseHandler<MainViewState, List<BlogPost>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        thrownExceptionIfApiServiceNotInitialized()

        return flow {
            val response = safeApiCall(Dispatchers.IO) { apiService.getAllBlogPosts() }

            emit(
                object : ApiResponseHandler<MainViewState, List<BlogPost>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        thrownExceptionIfApiServiceNotInitialized()

        return flow {
            val response = safeApiCall(Dispatchers.IO) { apiService.getCategories() }

            emit(
                object : ApiResponseHandler<MainViewState, List<Category>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<Category>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    categories = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    private fun thrownExceptionIfApiServiceNotInitialized() {
        if (!::apiService.isInitialized) {
            throw UninitializedPropertyAccessException(
                "Did you forget to set the ApiService in the FakeRepositoryImpl"
            )
        }
    }

}
