package com.bsrakdg.blogpost.di

import android.app.Application
import com.bsrakdg.blogpost.util.FakeGlideRequestManager
import com.bsrakdg.blogpost.util.GlideManager
import com.bsrakdg.blogpost.util.JsonUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestAppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(): GlideManager {
        return FakeGlideRequestManager()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideJsonUtil(application: Application): JsonUtil {
        return JsonUtil(application)
    }
}