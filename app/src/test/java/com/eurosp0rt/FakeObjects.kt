package com.eurosp0rt

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

fun fakeListVideosPostsObject(): List<Post> =
    listOf(fakePostObject(1, PostType.VIDEO), fakePostObject(2, PostType.VIDEO), fakePostObject(3, PostType.VIDEO))

fun fakeListStoriesPostsObject(): List<Post> =
    listOf(fakePostObject(4, PostType.STORY), fakePostObject(5, PostType.STORY), fakePostObject(6, PostType.STORY))

val successResourcePost = flowOf(Resource.success(fakePostObject(1, PostType.VIDEO)))
val successResourcePosts = flowOf(Resource.success(fakeListPostsObject()))
val errorResource = flowOf(Resource.error("Unknown error : broken", null))