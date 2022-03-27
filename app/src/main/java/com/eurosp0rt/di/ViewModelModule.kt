package com.eurosp0rt.di

import com.eurosp0rt.domain.usecases.GetPostUseCase
import com.eurosp0rt.domain.usecases.GetPostsUseCase
import com.eurosp0rt.ui.home.HomeViewModel
import com.eurosp0rt.ui.story.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { GetPostsUseCase(get()) }
    single { GetPostUseCase(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { StoryViewModel(get()) }
}