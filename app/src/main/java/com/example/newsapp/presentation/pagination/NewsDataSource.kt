package com.example.newsapp.presentation.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.api.NetworkState
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsDataSource (private val repository: NewsRepository,
                      private val query: String) : PageKeyedDataSource<Int, Article>() {

    private var networkState = MutableLiveData<NetworkState>()
    private var retry : (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    private fun executeCall(page: Int, perPage: Int, callback:(List<Article>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)

    }

}