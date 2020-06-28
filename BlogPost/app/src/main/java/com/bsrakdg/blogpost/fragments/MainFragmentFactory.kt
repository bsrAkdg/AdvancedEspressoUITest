package com.bsrakdg.blogpost.fragments

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bsrakdg.blogpost.util.GlideManager
import com.bsrakdg.blogpost.ui.DetailFragment
import com.bsrakdg.blogpost.ui.FinalFragment
import com.bsrakdg.blogpost.ui.ListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class MainFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory, requestManager)
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory, requestManager)
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(viewModelFactory, requestManager)
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}