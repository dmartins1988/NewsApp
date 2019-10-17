package com.example.newsapp.api.remote

import com.example.newsapp.api.service.NewsService

class NewsRemoteDataStore(
    private val newsService: NewsService
) {

    suspend fun getArticleByQuery(query: String,
                          page: Int,
                          pageSize: Int) = newsService.getAllArticlesByQuery(query, page, pageSize).await()

}