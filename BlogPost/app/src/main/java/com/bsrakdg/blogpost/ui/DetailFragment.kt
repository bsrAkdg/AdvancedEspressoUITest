package com.bsrakdg.blogpost.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bsrakdg.blogpost.R
import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.ui.viewmodel.MainViewModel
import com.bsrakdg.blogpost.util.GlideManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class DetailFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
) : Fragment(R.layout.fragment_detail) {

    private val className = "DetailFragment"

    private lateinit var uiCommunicationListener: UICommunicationListener

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        blog_image.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_finalFragment)
        }

        initUI()
    }

    private fun initUI(){
        uiCommunicationListener.showStatusBar()
        uiCommunicationListener.expandAppBar()
        uiCommunicationListener.hideCategoriesMenu()
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if(viewState != null){
                viewState.detailFragmentView.selectedBlogPost?.let{ selectedBlogPost ->
                    setBlogPostToView(selectedBlogPost)
                }
            }
        })
    }

    private fun setBlogPostToView(blogPost: BlogPost){
        requestManager
            .setImage(blogPost.image, blog_image)
        blog_title.text = blogPost.title
        blog_category.text = blogPost.category
        blog_body.text = blogPost.body
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUICommunicationListener(null)
    }

    fun setUICommunicationListener(mockUICommunicationListener: UICommunicationListener?) {

        // TEST: Set interface from mock
        if (mockUICommunicationListener != null) {
            this.uiCommunicationListener = mockUICommunicationListener
        }
        else{ // PRODUCTION: if no mock, get from context
            try {
                uiCommunicationListener = (context as UICommunicationListener)
            }catch (e: Exception){
                Log.e(className, "$context must implement UICommunicationListener")
            }
        }
    }
}


















