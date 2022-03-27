package com.eurosp0rt.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.repositories.PostRepository
import com.eurosp0rt.domain.usecases.GetPostUseCase
import com.eurosp0rt.domain.utils.Resource
import com.eurosp0rt.fakePostObject
import com.eurosp0rt.successResourcePost
import com.eurosp0rt.ui.story.StoryViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule

class StoryViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val postObserver: Observer<Resource<Post>> = mockk(relaxed = true)
    private val postRepository: PostRepository = mockk(relaxed = true)
    private val getPostUseCase: GetPostUseCase = GetPostUseCase(postRepository)
    private val storyViewModel: StoryViewModel = StoryViewModel(getPostUseCase)

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should send an success with post object to post livedata`() {
        coEvery { getPostUseCase(1) } returns successResourcePost
        storyViewModel.post.observeForever(postObserver)
        storyViewModel.getPost(1)
        coVerify { postObserver.onChanged(successResourcePost.first()) }
        runBlocking {
            assertThat(storyViewModel.post.value!!.data, equalTo(successResourcePost.first().data))
        }
    }

    @Test
    fun `should return title post`() {
        coEvery { getPostUseCase(1) } returns successResourcePost
        storyViewModel.getPost(1)
        val title = storyViewModel.getTextToShare()
        assertThat(title, equalTo(fakePostObject(1, PostType.STORY).title))
    }
}