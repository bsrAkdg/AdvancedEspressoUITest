package com.bsrakdg.blogpost.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    // for background tasks

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    fun isClear(): Boolean {
        return if (!countingIdlingResource.isIdleNow) {
            decrement()
            false
        } else {
            true
        }
    }
}