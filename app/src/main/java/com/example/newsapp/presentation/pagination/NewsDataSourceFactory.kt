package com.example.newsapp.presentation.pagination

import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope

class NewsDataSourceFactory(private val repository: NewsRepository,
                            private val scope: CoroutineScope)