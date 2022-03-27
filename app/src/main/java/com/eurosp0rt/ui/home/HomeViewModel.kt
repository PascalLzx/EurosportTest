package com.eurosp0rt.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurosp0rt.data.utils.mixWith
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.usecases.GetPostsUseCase
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _posts = MutableLiveData<Resource<List<Post>>>(Resource.loading())
    val posts: LiveData<Resource<List<Post>>>
        get() = _posts

    fun refreshData() {
        viewModelScope.launch {
            getPostsUseCase().collect {
                _posts.value = it
            }
        }
    }

    fun order(posts: List<Post>): List<Post> {
        val videos = posts.filter { it.postType == PostType.VIDEO }.sortedByDescending { it.date }
        val stories = posts.filter { it.postType == PostType.STORY }.sortedByDescending { it.date }
        return stories.mixWith(videos)
    }
}