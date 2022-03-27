package com.eurosp0rt.data.models

data class PostsResponse(
    val videos: List<VideoResponse>,
    val stories: List<StoryResponse>
)
