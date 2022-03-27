package com.eurosp0rt.domain.usecases

import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.repositories.PostRepository
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(private val postRepository: PostRepository) {

    operator fun invoke(id: Long): Flow<Resource<Post>> = postRepository.getPost(id)
}