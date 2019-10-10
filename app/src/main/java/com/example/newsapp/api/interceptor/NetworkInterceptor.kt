package com.example.newsapp.api.interceptor

import com.example.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val httpUrl =
            originalUrl.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
        val finalUrlBuilder = originalRequest.newBuilder().url(httpUrl)
        return chain.proceed(finalUrlBuilder.build())
    }

}