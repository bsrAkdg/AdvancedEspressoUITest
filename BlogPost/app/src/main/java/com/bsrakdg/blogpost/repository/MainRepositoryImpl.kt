package com.bsrakdg.blogpost.repository

import com.bsrakdg.blogpost.api.ApiService
import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.models.Category
import com.bsrakdg.blogpost.ui.viewmodel.state.MainViewState
import com.bsrakdg.blogpost.util.ApiResponseHandler
import com.bsrakdg.blogpost.util.DataState
import com.bsrakdg.blogpost.util.StateEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class  MainRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : MainRepository{

    private val CLASS_NAME = "MainRepositoryImpl"

    override fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getBlogPosts(category)}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
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

    override fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getAllBlogPosts()}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
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

    override fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getCategories()}

            emit(
                object: ApiResponseHandler<MainViewState, List<Category>>(
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

}


