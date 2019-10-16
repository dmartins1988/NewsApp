package com.example.newsapp.presentation.ui.main

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.api.NetworkState
import com.example.newsapp.model.Article
import com.example.newsapp.presentation.pagination.NewsDataSourceFactory
import com.example.newsapp.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository,
                    private val query: String) : ViewModel() {

    private val newsDataSource = NewsDataSourceFactory(repository = repository, scope = viewModelScope)

    val news = LivePagedListBuilder(newsDataSource, config()).build()
    val networkState: LiveData<NetworkState>? = Transformations.switchMap(newsDataSource.source) { it.getNetworkState() }

    fun fetchQuery(query: String) {
        val searchQuery = query.trim()
        newsDataSource.updateQuery(searchQuery)
    }

    fun refreshFailedRequest() = newsDataSource.getSource()?.refreshFailedQuery()

    fun refreshAllList() = newsDataSource.getSource()?.refresh()

    fun getCurrentyQuery() = newsDataSource.getQuery()

    private fun config() = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(5)
        .setInitialLoadSizeHint(5 * 2)
        .build()
    
}
