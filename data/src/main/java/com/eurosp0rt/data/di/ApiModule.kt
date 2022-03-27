package com.eurosp0rt.data.di

import com.eurosp0rt.data.api.Eurosp0rtService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit>().create(Eurosp0rtService::class.java)
    }
}