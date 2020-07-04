package com.bsrakdg.blogpost.fragments

import androidx.fragment.app.FragmentFactory
import com.bsrakdg.blogpost.ui.DetailFragment
import com.bsrakdg.blogpost.ui.FinalFragment
import com.bsrakdg.blogpost.ui.ListFragment
import com.bsrakdg.blogpost.ui.UICommunicationListener
import com.bsrakdg.blogpost.util.GlideManager
import com.bsrakdg.blogpost.viewmodels.FakeMainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class FakeMainFragmentFactory
@Inject
constructor(
    private val viewModelFactory: FakeMainViewModelFactory,
    private val requestManager: GlideManager
) : FragmentFactory() {

    // used for setting a mock<UICommunicationListener>
    lateinit var uiCommunicationListener: UICommunicationListener

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}
