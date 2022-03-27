package com.eurosp0rt.data.repositories

import com.eurosp0rt.data.api.Eurosp0rtService
import com.eurosp0rt.data.database.dao.PostDao
import com.eurosp0rt.data.mappers.toDomainModel
import com.eurosp0rt.data.mappers.toEntityModel
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.repositories.PostRepository
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postDao: PostDao,
    private val apiService: Eurosp0rtService
) : PostRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.loading())
        postDao.getAll().let {
            if (it.isNotEmpty())
                emit(Resource.success(it.map { postEntity ->
                    postEntity.toDomainModel()
                }))
        }
        try {
            val posts = apiService.getPosts()
            val videosEntities = posts.videos.map {
                it.toEntityModel()
            }.sortedBy { it.date }
            val storiesEntities = posts.stories.map {
                it.toEntityModel()
            }.sortedBy { it.date }
            postDao.insertAll(videosEntities+storiesEntities)
            emit(Resource.success(postDao.getAll().map {
                it.toDomainModel()
            }))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    emit(Resource.error("Network Error"))
                }
                is HttpException -> {
                    val codeError = throwable.code()
                    val errorMessageResponse = throwable.message()
                    emit(Resource.error("Error $errorMessageResponse : $codeError"))
                }
                else -> {
                    emit(Resource.error("Unknown error : ${throwable.message}"))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getPost(id: Long): Flow<Resource<Post>> = flow {
        emit(Resource.loading())
        val post = postDao.get(id)
        if (post != null)
            emit(Resource.success(post.toDomainModel()))
        else
            emit(Resource.error("Post not found"))
    }.flowOn(Dispatchers.IO)
}