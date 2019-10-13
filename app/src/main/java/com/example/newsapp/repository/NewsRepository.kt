package com.example.newsapp.repository

import com.example.newsapp.api.remote.NewsRemoteDataStore
import com.example.newsapp.model.Article

class NewsRepository(
    private val newsRemoteDataStore: NewsRemoteDataStore)
{

    suspend fun getArticleByQuery(query: String,
                                  page: Int,
                                  pageSize: Int) : List<Article> {
        if (query.isEmpty()) return listOf()
        val listOfArticle = mutableListOf<Article>()
        val request = newsRemoteDataStore.getArticleByQuery(query, page, pageSize)

        request.articles.forEach {
            article -> listOfArticle.add(article)
        }
        return listOfArticle
    }
}