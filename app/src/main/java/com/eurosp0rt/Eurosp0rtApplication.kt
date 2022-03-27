package com.eurosp0rt

import android.app.Application
import com.eurosp0rt.data.di.apiModule
import com.eurosp0rt.data.di.databaseModule
import com.eurosp0rt.data.di.networkModule
import com.eurosp0rt.data.di.repositoryModule
import com.eurosp0rt.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class Eurosp0rtApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@Eurosp0rtApplication)
            modules(
                networkModule,
                repositoryModule,
                apiModule,
                databaseModule,
                viewModelModule
            )
        }
    }
}