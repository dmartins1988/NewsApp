package com.example.newsapp.presentation.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope

class NewsDataSourceFactory(private val repository: NewsRepository,
                            private var query: String = "",
                            private val scope: CoroutineScope) : DataSource.Factory<Int, Article>() {

    val source = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, Article> {
        val source = NewsDataSource(repository, query, scope)
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value

    fun getQuery() = query

    fun updateQuery(newQuery: String) {
        this.query = newQuery
        getSource()?.refresh()
    }

}