package com.example.newsapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.presentation.pagination.NewsDataSourceFactory
import com.example.newsapp.repository.NewsRepository

class NewsViewModel(repository: NewsRepository) : ViewModel() {

    private val newsDataSource = NewsDataSourceFactory(repository = repository, scope = viewModelScope)

    
}
