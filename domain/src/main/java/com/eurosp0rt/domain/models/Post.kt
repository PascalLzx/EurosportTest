package com.eurosp0rt.domain.models

data class Post(
    val id: Long,
    val postType: PostType,
    val title: String,
    val date: Double,
    val sport: Sport,
    // story
    val author: String?,
    val teaser: String?,
    val image: String?,
    // video
    val thumb: String?,
    val url: String?,
    val views: Long?
)