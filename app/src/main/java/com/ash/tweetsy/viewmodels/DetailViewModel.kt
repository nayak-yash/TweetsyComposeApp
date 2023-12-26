package com.ash.tweetsy.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.tweetsy.models.Tweet
import com.ash.tweetsy.repository.TweetsyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TweetsyRepository,
    private val stateSavedStateHandle: SavedStateHandle
): ViewModel() {

    val tweets: StateFlow<List<Tweet>> get() = repository.tweets

    init {
        viewModelScope.launch {
            val category = stateSavedStateHandle.get<String>("category") ?: "motivation"
            repository.getTweets(category)
        }
    }
}