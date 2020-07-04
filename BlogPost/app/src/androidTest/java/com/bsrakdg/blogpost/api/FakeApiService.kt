package com.bsrakdg.blogpost.api

import com.bsrakdg.blogpost.models.BlogPost
import com.bsrakdg.blogpost.models.Category
import com.bsrakdg.blogpost.util.Constants
import com.bsrakdg.blogpost.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeApiService
@Inject
constructor(
    private val jsonUtil: JsonUtil
) : ApiService {

    var blogPostJsonFileName: String = Constants.BLOG_POSTS_DATA_FILENAME
    var categoriesJsonFileName: String = Constants.CATEGORIES_DATA_FILENAME
    var networkDelay: Long = 0L


    override suspend fun getBlogPosts(category: String): List<BlogPost> {
        val rawJson = jsonUtil.readJSONFromAsset(blogPostJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )

        val filteredBlogPosts = blogs.filter { blogPost ->
            blogPost.category.equals(category)
        }
        delay(networkDelay)
        return filteredBlogPosts
    }

    override suspend fun getAllBlogPosts(): List<BlogPost> {
        val rawJson = jsonUtil.readJSONFromAsset(blogPostJsonFileName)
        val blogs = Gson().fromJson<List<BlogPost>>(
            rawJson,
            object : TypeToken<List<BlogPost>>() {}.type
        )
        delay(networkDelay)
        return blogs
    }

    override suspend fun getCategories(): List<Category> {
        val rawJson = jsonUtil.readJSONFromAsset(categoriesJsonFileName)
        val categories = Gson().fromJson<List<Category>>(
            rawJson,
            object : TypeToken<List<Category>>() {}.type
        )
        delay(networkDelay)
        return categories
    }

}