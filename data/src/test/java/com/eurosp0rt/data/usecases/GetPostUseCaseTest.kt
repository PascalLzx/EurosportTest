package com.eurosp0rt.data.usecases

import com.eurosp0rt.data.*
import com.eurosp0rt.data.repositories.PostRepositoryImpl
import com.eurosp0rt.domain.usecases.GetPostUseCase
import com.eurosp0rt.domain.usecases.GetPostsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GetPostUseCaseTest {

    private val postRepository: PostRepositoryImpl = mockk()
    private val getPostUseCase = GetPostUseCase(postRepository)

    @Test
    fun `should return post`() {
        coEvery { postRepository.getPost(1) } returns successResourcePost
        val result = getPostUseCase(1)
        coVerify { postRepository.getPost(1) }
        assertThat(result, equalTo(successResourcePost))
    }
}