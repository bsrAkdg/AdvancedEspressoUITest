package com.bsrakdg.blogpost.di

import android.app.Application
import com.bsrakdg.blogpost.util.GlideManager
import com.bsrakdg.blogpost.util.GlideRequestManager
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/* Alternative for Test: 'TestAppModule' */
@Module
object AppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application
    ): GlideManager {
        return GlideRequestManager(
            Glide.with(application)
        )
    }
}
