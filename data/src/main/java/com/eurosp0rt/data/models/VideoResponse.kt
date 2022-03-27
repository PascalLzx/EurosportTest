package com.eurosp0rt.data.models

data class VideoResponse(
    val id: Long,
    val title: String,
    val thumb: String,
    val url: String,
    val date: Double,
    val sport: SportResponse,
    val views: Long
)