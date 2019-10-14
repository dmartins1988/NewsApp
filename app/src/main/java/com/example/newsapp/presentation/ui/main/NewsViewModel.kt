package com.example.newsapp.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.NetworkState
import com.example.newsapp.presentation.pagination.NewsDataSourceFactory
import com.example.newsapp.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository,
                    private val query: String) : ViewModel() {

    private val newsDataSource = NewsDataSourceFactory(repository = repository, scope = viewModelScope)



    
}
