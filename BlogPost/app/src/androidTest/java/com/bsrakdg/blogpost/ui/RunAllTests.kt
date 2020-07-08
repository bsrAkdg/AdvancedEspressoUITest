package com.bsrakdg.blogpost.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainNavigationTest::class,
    ListFragmentNavigationTest::class,
    ListFragmentIntegrationTests::class,
    ListFragmentErrorTests::class,
    DetailFragmentTest::class
)
class RunAllTests