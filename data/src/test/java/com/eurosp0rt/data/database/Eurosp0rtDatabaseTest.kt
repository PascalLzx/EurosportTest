package com.eurosp0rt.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.eurosp0rt.data.database.dao.PostDao
import com.eurosp0rt.data.fakeListPostsEntityObject
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.hamcrest.MatcherAssert.assertThat

@Config(manifest=Config.NONE, sdk = [30])
@RunWith(RobolectricTestRunner::class)
class Eurosp0rtDatabaseTest {

    private lateinit var appDatabase: Eurosp0rtDatabase
    private lateinit var postDao: PostDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Eurosp0rtDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        postDao = appDatabase.postDao
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun `should return an empty list`() = runBlocking {
            assertThat(postDao.getAll(), `is`(emptyList()))
        }

    @Test
    fun `should return posts inserted`() = runBlocking {
            postDao.insertAll(fakeListPostsEntityObject())
            assertThat(postDao.getAll(), `is`(fakeListPostsEntityObject()))
        }

    @Test
    fun `should return post inserted`() = runBlocking {
        postDao.insertAll(fakeListPostsEntityObject())
        assertThat(postDao.get(fakeListPostsEntityObject()[1].id), `is`(fakeListPostsEntityObject()[1]))
    }

    @Test
    fun `should return an empty list after delete`() = runBlocking {
        postDao.insertAll(fakeListPostsEntityObject())
        assertThat(postDao.getAll(), `is`(fakeListPostsEntityObject()))
        postDao.deleteAll()
        assertThat(postDao.getAll(), `is`(emptyList()))
    }
}