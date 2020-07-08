package com.bsrakdg.blogpost.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.bsrakdg.blogpost.R
import com.bsrakdg.blogpost.TestBaseApplication
import com.bsrakdg.blogpost.di.TestAppComponent
import com.bsrakdg.blogpost.fragments.FakeMainFragmentFactory
import com.bsrakdg.blogpost.ui.BlogPostListAdapter.BlogPostViewHolder
import com.bsrakdg.blogpost.util.Constants
import com.bsrakdg.blogpost.util.EspressoIdlingResourceRule
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.test.assertEquals

/**
 * https://developer.android.com/guide/navigation/navigation-testing
 */
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentNavigationTest : BaseMainActivityTest() {

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var fragmentFactory: FakeMainFragmentFactory

    private val uiCommunicationListener = mockk<UICommunicationListener>()

    @Before
    fun init() {
        every { uiCommunicationListener.showStatusBar() } just runs
        every { uiCommunicationListener.expandAppBar() } just runs
        every { uiCommunicationListener.hideStatusBar() } just runs
        every { uiCommunicationListener.showCategoriesMenu(any()) } just runs
    }

    @Test
    fun testNavigationToDetailFragment() {
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

        fragmentFactory.uiCommunicationListener = uiCommunicationListener

        val navController = TestNavHostController(
            app
        )
        navController.setGraph(R.navigation.main_nav_graph)
        navController.setCurrentDestination(R.id.listFragment)

        val scenario = launchFragmentInContainer<ListFragment>(
            factory = fragmentFactory
        )

        scenario.onFragment { listFragment ->
            Navigation.setViewNavController(listFragment.requireView(), navController)
        }

        val recyclerView = onView(withId(R.id.recycler_view))

        recyclerView.check(matches(isDisplayed()))

        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<BlogPostViewHolder>(5)
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<BlogPostViewHolder>(5, click())
        )

        assertEquals(navController.currentDestination?.id, R.id.detailFragment)
    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent).inject(this)
    }

}