package com.example.newsapp.api.service

import com.example.newsapp.model.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/everything")
    fun getAllArticlesByQuery(@Query("q") query: String,
                              @Query("page") page: Int,
                              @Query("pageSize") pageSize: Int) : Deferred<NewsResponse>

}