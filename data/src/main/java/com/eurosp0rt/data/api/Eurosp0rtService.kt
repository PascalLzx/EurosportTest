package com.eurosp0rt.data.api

import com.eurosp0rt.data.models.PostsResponse
import retrofit2.http.GET

interface Eurosp0rtService {

    @GET("api/json-storage/bin/edfefba")
    suspend fun getPosts(): PostsResponse
}
