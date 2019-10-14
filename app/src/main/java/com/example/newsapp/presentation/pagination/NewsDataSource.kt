package com.example.newsapp.presentation.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.api.NetworkState
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsDataSource(private val repository: NewsRepository,
                     private val query: String,
                     private val coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {

    private var networkState = MutableLiveData<NetworkState>()
    private var retry : (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        retry = { loadInitial(params, callback) }
        executeCall(1, params.requestedLoadSize) { articles ->
            callback.onResult(articles, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        val page = params.key
        retry = { loadAfter(params, callback) }
        executeCall(page, params.requestedLoadSize) { articles ->
            callback.onResult(articles, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    private fun executeCall(page: Int, perPage: Int, callback:(List<Article>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
        coroutineScope.launch(getJobErrorHandler()) {
            val articles = repository.getArticleByQuery(query, page, perPage)
            retry = null
            networkState.postValue(NetworkState.SUCCESS)
            callback(articles)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler {_, _->
        networkState.postValue(NetworkState.FAILED)
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() {
        this.invalidate()
    }

    fun refreshFailedQuery() {
        val prevQuery = retry
        retry = null
        prevQuery?.invoke()
    }
}