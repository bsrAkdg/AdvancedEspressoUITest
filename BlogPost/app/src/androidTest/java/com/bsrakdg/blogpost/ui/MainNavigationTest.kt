package com.bsrakdg.blogpost.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.bsrakdg.blogpost.R
import com.bsrakdg.blogpost.TestBaseApplication
import com.bsrakdg.blogpost.di.TestAppComponent
import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.ui.BlogPostListAdapter.BlogPostViewHolder
import com.bsrakdg.blogpost.util.Constants
import com.bsrakdg.blogpost.util.EspressoIdlingResourceRule
import com.bsrakdg.blogpost.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MainNavigationTest : BaseMainActivityTest() {

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Test
    fun basicNavigationTest() { // end to end navigation testing
        // setup
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext.applicationContext as TestBaseApplication


        val apiService = configureFakeApiService(
            blogDataSource = Constants.BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = Constants.CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(
            apiService = apiService,
            application = app
        )

        injectTest(app)

        val rawJson = jsonUtil.readJSONFromAsset(Constants.BLOG_POSTS_DATA_FILENAME)
        val blog = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )

        val selectedListIndex = 8
        val selectedBlogPost = blog[selectedListIndex]

        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(selectedListIndex)
        )

        // nav to detail fragment
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostViewHolder>(
                selectedListIndex,
                click()
            )
        )

        onView(withId(R.id.blog_title)).check(matches(withText(selectedBlogPost.title)))

        onView(withId(R.id.blog_category)).check(matches(withText(selectedBlogPost.category)))

        onView(withId(R.id.blog_body)).check(matches(withText(selectedBlogPost.body)))

        // nav to final fragment
        onView(withId(R.id.blog_image)).perform(click())

        onView(withId(R.id.scaling_image_view)).check(matches(isDisplayed()))

        // back to detail fragment
        pressBack()

        onView(withId(R.id.blog_title)).check(matches(withText(selectedBlogPost.title)))

        // back to list fragment
        pressBack()

        recyclerView.check(matches(isDisplayed()))
    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent).inject(this)
    }

}