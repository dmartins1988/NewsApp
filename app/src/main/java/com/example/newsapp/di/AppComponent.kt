package com.example.newsapp.di

import com.example.newsapp.di.module.NetworkModule
import com.example.newsapp.di.module.RemoteModule
import com.example.newsapp.di.module.RepositoryModule
import com.example.newsapp.di.module.ViewModelModule

val AppComponent = listOf(NetworkModule, RemoteModule, RepositoryModule, ViewModelModule)