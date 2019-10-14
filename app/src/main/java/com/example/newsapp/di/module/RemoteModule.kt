package com.example.newsapp.di.module

import com.example.newsapp.api.remote.NewsRemoteDataStore
import org.koin.dsl.module

val RemoteModule = module {
    factory { NewsRemoteDataStore(get()) }
}