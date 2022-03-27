package com.eurosp0rt.data.usecases

import com.eurosp0rt.data.*
import com.eurosp0rt.data.repositories.PostRepositoryImpl
import com.eurosp0rt.domain.usecases.GetPostsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GetPostsUseCaseTest {

    private val postRepository: PostRepositoryImpl = mockk()
    private val getPostsUseCase = GetPostsUseCase(postRepository)

    @Test
    fun `should return posts list`() {
        coEvery { postRepository.getPosts() } returns successResourcePosts
        val result = getPostsUseCase()
        coVerify { postRepository.getPosts() }
        assertThat(result, equalTo(successResourcePosts))
    }
}