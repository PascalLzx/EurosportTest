package com.eurosp0rt.data.mappers

import com.eurosp0rt.data.database.entities.PostEntity
import com.eurosp0rt.data.database.entities.SportEntity
import com.eurosp0rt.data.models.SportResponse
import com.eurosp0rt.data.models.StoryResponse
import com.eurosp0rt.data.models.VideoResponse
import com.eurosp0rt.domain.models.PostType

fun VideoResponse.toEntityModel(): PostEntity = PostEntity(
    id,
    PostType.VIDEO,
    title,
    date,
    sport.toEntityModel(),
    null,
    null,
    null,
    thumb,
    url,
    views
)

fun StoryResponse.toEntityModel(): PostEntity = PostEntity(
    id,
    PostType.STORY,
    title,
    date,
    sport.toEntityModel(),
    author,
    teaser,
    image,
    null,
    null,
    null
)

fun SportResponse.toEntityModel(): SportEntity = SportEntity(id, name)