package com.eurosp0rt.data.mappers

import com.eurosp0rt.data.database.entities.PostEntity
import com.eurosp0rt.data.database.entities.SportEntity
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.Sport

fun PostEntity.toDomainModel(): Post = Post(
    id,
    postType,
    title,
    date,
    sport.toDomainModel(),
    author,
    teaser,
    image,
    thumb,
    url,
    views
)

fun SportEntity.toDomainModel(): Sport = Sport(id, name)