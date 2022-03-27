package com.eurosp0rt.data.di

import com.eurosp0rt.data.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        provideLoggingInterceptor()
    }
    single {
        provideOkHttpClient(get())
    }
    single {
        provideMoshi()
    }
    single {
        provideRetrofit(get(), get())
    }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message -> Timber.d(message) }.setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val timeoutInSeconds = 120
    val okHttpBuilder = OkHttpClient.Builder()
    okHttpBuilder.addInterceptor(loggingInterceptor)
        .connectTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
    return okHttpBuilder.build()
}

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
}