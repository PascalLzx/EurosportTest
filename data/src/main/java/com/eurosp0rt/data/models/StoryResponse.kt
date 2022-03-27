package com.eurosp0rt.data.models

data class StoryResponse(
    val id: Long,
    val title: String,
    val teaser: String,
    val image: String,
    val date: Double,
    val author: String,
    val sport: SportResponse
)
