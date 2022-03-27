package com.eurosp0rt.data.repositories

import app.cash.turbine.test
import com.eurosp0rt.data.*
import com.eurosp0rt.data.api.Eurosp0rtService
import com.eurosp0rt.data.database.dao.PostDao
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PostRepositoryTest {

    private val postDao: PostDao = mockk(relaxed = true)
    private val eurosp0rtService: Eurosp0rtService = mockk()
    private val postRepository = PostRepositoryImpl(postDao, eurosp0rtService)

    @Test
    fun `should emit posts from database and server`() {
        coEvery { postDao.getAll() } returns fakeListPostsEntityObject()
        coEvery { eurosp0rtService.getPosts() } returns fakePostsResponseObject()
        runBlocking {
            postRepository.getPosts().test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(successResourcePosts.first(), awaitItem())
                assertEquals(successResourcePosts.first(), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit posts from database with an error server`() {
        coEvery { postDao.getAll() } returns fakeListPostsEntityObject()
        coEvery { eurosp0rtService.getPosts() } throws RuntimeException("broken")
        runBlocking {
            postRepository.getPosts().test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(successResourcePosts.first(), awaitItem())
                assertEquals(errorResource.first().message, awaitItem().message)
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit posts from server`() {
        coEvery { postDao.getAll() } returnsMany listOf(emptyList(), fakeListPostsEntityObject())
        coEvery { eurosp0rtService.getPosts() } returns fakePostsResponseObject()
        runBlocking {
            postRepository.getPosts().test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(successResourcePosts.first(), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit an error server`() {
        coEvery { postDao.getAll() } returns emptyList()
        coEvery { eurosp0rtService.getPosts() } throws RuntimeException("broken")
        runBlocking {
            postRepository.getPosts().test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(errorResource.first().message, awaitItem().message)
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit an empty list from the server`() {
        coEvery { postDao.getAll() } returns emptyList()
        coEvery { eurosp0rtService.getPosts() } returns fakePostsResponseEmptyObject()
        runBlocking {
            postRepository.getPosts().test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(successResourcePostEmpty.first(), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit a post from the database`() {
        coEvery { postDao.get(1) } returns fakePostEntityObject(1, PostType.VIDEO)
        runBlocking {
            postRepository.getPost(1).test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(successResourcePost.first(), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `should emit an error from the database`() {
        coEvery { postDao.get(1) } returns null
        runBlocking {
            postRepository.getPost(1).test {
                assertEquals(Resource.loading(null), awaitItem())
                assertEquals(errorResourceDatabase.first(), awaitItem())
                awaitComplete()
            }
        }
    }
}