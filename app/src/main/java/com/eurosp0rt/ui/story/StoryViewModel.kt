package com.eurosp0rt.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.usecases.GetPostUseCase
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StoryViewModel(
    private val getPostUseCase: GetPostUseCase
): ViewModel() {

    private val _post = MutableLiveData<Resource<Post>>()
    val post: LiveData<Resource<Post>>
        get() = _post

    fun getPost(id: Long) {
        viewModelScope.launch {
            getPostUseCase(id).collect {
                _post.value = it
            }
        }
    }

    fun getTextToShare(): String? {
        val p = post.value
        return p?.data?.title
    }
}