package com.eurosp0rt.domain.usecases

import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.repositories.PostRepository
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke(): Flow<Resource<List<Post>>> = postRepository.getPosts()
}