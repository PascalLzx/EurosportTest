package com.eurosp0rt.data.di

import androidx.room.Room
import com.eurosp0rt.data.database.Eurosp0rtDatabase
import com.eurosp0rt.data.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), Eurosp0rtDatabase::class.java,
            BuildConfig.DATABASE_NAME)
            //.allowMainThreadQueries()
            //.fallbackToDestructiveMigration()
            .build()
    }

    single { get<Eurosp0rtDatabase>().postDao }
}