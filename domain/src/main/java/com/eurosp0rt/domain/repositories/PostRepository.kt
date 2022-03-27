package com.eurosp0rt.domain.repositories

import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
    fun getPost(id: Long): Flow<Resource<Post>>
}