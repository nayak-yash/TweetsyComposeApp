package com.ash.tweetsy.repository

import android.util.Log
import com.ash.tweetsy.api.TweetsyApi
import com.ash.tweetsy.models.Tweet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetsyRepository @Inject constructor(
    private val tweetsyApi: TweetsyApi
) {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories

    private val _tweets = MutableStateFlow<List<Tweet>>(emptyList())
    val tweets: StateFlow<List<Tweet>> get() = _tweets

    suspend fun getCategories() {
        try {
            val response = tweetsyApi.getCategories()
            if (response.isSuccessful && response.body() != null) {
                _categories.emit(response.body()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getTweets(category: String) {
        try {
            val response = tweetsyApi.getTweets("tweets[?(@.category==\"$category\")]")
            if (response.isSuccessful && response.body() != null) {
                _tweets.emit(response.body()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
