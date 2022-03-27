package com.eurosp0rt.data

import com.eurosp0rt.data.database.entities.PostEntity
import com.eurosp0rt.data.database.entities.SportEntity
import com.eurosp0rt.data.models.PostsResponse
import com.eurosp0rt.data.models.SportResponse
import com.eurosp0rt.data.models.StoryResponse
import com.eurosp0rt.data.models.VideoResponse
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.models.Sport
import com.eurosp0rt.domain.utils.Resource
import kotlinx.coroutines.flow.flowOf

fun fakePostObject(id: Long, postType: PostType): Post = Post(
    id, postType, "title", 19.0, fakeSportObject(), "lzx", "teaser", "image", "", "", 12
)

fun fakeSportObject(): Sport = Sport(1, "football")

fun fakeListPostsObject(): List<Post> = listOf(fakePostObject(1, PostType.STORY), fakePostObject(2, PostType.VIDEO))

fun fakePostEntityObject(id: Long, postType: PostType): PostEntity = PostEntity(
    id, postType, "title", 19.0, fakeSportEntityObject(), "lzx", "teaser", "image", "", "", 12
)

fun fakeSportEntityObject(): SportEntity = SportEntity(1, "football")

fun fakeListPostsEntityObject(): List<PostEntity> = listOf(
    fakePostEntityObject(1, PostType.STORY), fakePostEntityObject(2, PostType.VIDEO)
)

fun fakePostsResponseObject(): PostsResponse = PostsResponse(
    fakeListVideoObject(), fakeListStoryObject()
)

fun fakePostsResponseEmptyObject(): PostsResponse = PostsResponse(
    emptyList(), emptyList()
)

fun fakeVideoResponseObject(id: Long): VideoResponse = VideoResponse(
    id,"title", "thumb","url",19.0, fakeSportResponseObject(),12
)

fun fakeStoryResponseObject(id: Long): StoryResponse = StoryResponse(
    id, "title", "teaser","image",19.0, "lzx", fakeSportResponseObject()
)

fun fakeSportResponseObject(): SportResponse = SportResponse(1, "football")

fun fakeListVideoObject(): List<VideoResponse> = listOf(
    fakeVideoResponseObject(1), fakeVideoResponseObject(2)
)

fun fakeListStoryObject(): List<StoryResponse> = listOf(
    fakeStoryResponseObject(3), fakeStoryResponseObject(4)
)

val successResourcePost = flowOf(Resource.success(fakePostObject(1, PostType.VIDEO)))
val successResourcePosts = flowOf(Resource.success(fakeListPostsObject()))
val successResourcePostEmpty = flowOf(Resource.success(listOf<Post>()))
val errorResource = flowOf(Resource.error("Unknown error : broken", null))