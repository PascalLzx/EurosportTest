package com.eurosp0rt.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.repositories.PostRepository
import com.eurosp0rt.domain.usecases.GetPostsUseCase
import com.eurosp0rt.domain.utils.Resource
import com.eurosp0rt.errorResource
import com.eurosp0rt.fakeListStoriesPostsObject
import com.eurosp0rt.fakeListVideosPostsObject
import com.eurosp0rt.successResourcePosts
import com.eurosp0rt.ui.home.HomeViewModel
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

class HomeViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val postsObserver: Observer<Resource<List<Post>>> = mockk(relaxed = true)
    private val postRepository: PostRepository = mockk(relaxed = true)
    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase(postRepository)
    private val homeViewModel: HomeViewModel = HomeViewModel(getPostsUseCase)

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
    fun `should send an error to post livedata`() {
        coEvery { getPostsUseCase() } returns errorResource
        homeViewModel.posts.observeForever(postsObserver)
        homeViewModel.refreshData()
        coVerify { postsObserver.onChanged(errorResource.first()) }
        runBlocking {
            assertThat(homeViewModel.posts.value!!.data, equalTo(errorResource.first().data)
            )
        }
    }

    @Test
    fun `should send an success with post list to post livedata`() {
        coEvery { getPostsUseCase() } returns successResourcePosts
        homeViewModel.posts.observeForever(postsObserver)
        homeViewModel.refreshData()
        coVerify { postsObserver.onChanged(successResourcePosts.first()) }
        runBlocking {
            assertThat(homeViewModel.posts.value!!.data, equalTo(successResourcePosts.first().data)
            )
        }
    }

    @Test
    fun `should return a mixed (story-video-story-video) list`() {
        val mixedList = homeViewModel.order(fakeListStoriesPostsObject()+fakeListVideosPostsObject())
        assertThat(mixedList[0].postType, equalTo(PostType.STORY))
        assertThat(mixedList[1].postType, equalTo(PostType.VIDEO))
        assertThat(mixedList[2].postType, equalTo(PostType.STORY))
        assertThat(mixedList[3].postType, equalTo(PostType.VIDEO))
        assertThat(mixedList[4].postType, equalTo(PostType.STORY))
    }
}