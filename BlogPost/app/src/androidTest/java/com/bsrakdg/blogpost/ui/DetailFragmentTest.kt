package com.bsrakdg.blogpost.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.bsrakdg.blogpost.R
import com.bsrakdg.blogpost.TestBaseApplication
import com.bsrakdg.blogpost.di.TestAppComponent
import com.bsrakdg.blogpost.fragments.FakeMainFragmentFactory
import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.ui.viewmodel.setSelectedBlogPost
import com.bsrakdg.blogpost.util.Constants.BLOG_POSTS_DATA_FILENAME
import com.bsrakdg.blogpost.util.Constants.CATEGORIES_DATA_FILENAME
import com.bsrakdg.blogpost.util.FakeGlideRequestManager
import com.bsrakdg.blogpost.util.JsonUtil
import com.bsrakdg.blogpost.viewmodels.FakeMainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailFragmentTest : BaseMainActivityTest() {

    @Inject
    lateinit var viewModelFactory: FakeMainViewModelFactory

    @Inject
    lateinit var requestManager: FakeGlideRequestManager

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Inject
    lateinit var fragmentFactory: FakeMainFragmentFactory

    private val uiCommunicationListener = mockk<UICommunicationListener>()

    @Before // each test
    fun init() {
        every { uiCommunicationListener.showStatusBar() } just runs // don't do anything
        every { uiCommunicationListener.expandAppBar() } just runs // don't do anything
        every { uiCommunicationListener.hideCategoriesMenu() } just runs // don't do anything

    }

    @Test
    fun isSelectedBlogPostDetailsSet() {

        // setup
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestBaseApplication // application context

        val apiService = configureFakeApiService(
            blogDataSource = BLOG_POSTS_DATA_FILENAME,
            categoriesDataSource = CATEGORIES_DATA_FILENAME,
            networkDelay = 0L,
            application = app
        )

        configureFakeRepository(apiService, app)

        injectTest(app)

        fragmentFactory.uiCommunicationListener = uiCommunicationListener

        // run test
        val scenario = launchFragmentInContainer<DetailFragment>(
            factory = fragmentFactory
        )

        val rawJson = jsonUtil.readJSONFromAsset(BLOG_POSTS_DATA_FILENAME)
        val blog = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )

        val selectedBlogPost = blog[0] // does not matter

        scenario.onFragment { detailFragment ->
            detailFragment.viewModel.setSelectedBlogPost(selectedBlogPost)
        }

        onView(withId(R.id.blog_title))
            .check(matches(withText(selectedBlogPost.title)))

        onView(withId(R.id.blog_category))
            .check(matches(withText(selectedBlogPost.category)))

        onView(withId(R.id.blog_body))
            .check(matches(withText(selectedBlogPost.body)))

    }

    override fun injectTest(application: TestBaseApplication) {
        (application.appComponent as TestAppComponent).inject(this)
    }
}