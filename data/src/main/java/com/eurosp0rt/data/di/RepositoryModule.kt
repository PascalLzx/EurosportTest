package com.eurosp0rt.data.di

import com.eurosp0rt.data.repositories.PostRepositoryImpl
import com.eurosp0rt.domain.repositories.PostRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
}