package com.example.newsapp.di.module

import com.example.newsapp.repository.NewsRepository
import org.koin.dsl.module.module

val RepositoryModule = module {
    factory { NewsRepository(get()) }
}