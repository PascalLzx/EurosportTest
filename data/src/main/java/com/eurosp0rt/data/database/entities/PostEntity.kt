package com.eurosp0rt.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eurosp0rt.domain.models.PostType

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey val id: Long,
    val postType: PostType,
    val title: String,
    val date: Double,
    val sport: SportEntity,
    // story
    val author: String?,
    val teaser: String?,
    val image: String?,
    // video
    val thumb: String?,
    val url: String?,
    val views: Long?
)

data class SportEntity(val id: Long, val name: String)
