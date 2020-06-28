package com.bsrakdg.blogpost.repository

import com.bsrakdg.blogpost.util.DataState
import com.bsrakdg.blogpost.util.StateEvent
import com.bsrakdg.blogpost.ui.viewmodel.state.MainViewState
import kotlinx.coroutines.flow.Flow

interface MainRepository{

    fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>>

    fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>>

    fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>>
}