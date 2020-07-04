package com.bsrakdg.blogpost.util

import android.widget.ImageView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeGlideRequestManager
@Inject
constructor() : GlideManager {
    override fun setImage(imageUrl: String, imageView: ImageView) {
        // does nothing (because, espresso does not test glide)
    }
}