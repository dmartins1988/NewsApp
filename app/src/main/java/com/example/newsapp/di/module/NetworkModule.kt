package com.example.newsapp.di.module

import com.example.newsapp.BuildConfig
import com.example.newsapp.api.interceptor.NetworkInterceptor
import com.example.newsapp.api.service.NewsService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {

    factory<Interceptor> { NetworkInterceptor() }

    factory { OkHttpClient.Builder().addInterceptor(get()).build() }

    single { Retrofit.Builder()
        .client(get())
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    }

    factory { get<Retrofit>().create(NewsService::class.java) }

}