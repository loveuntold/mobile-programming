package com.example.news_app.data.repository

import com.example.news_app.data.api.RetrofitClient
import com.example.news_app.data.model.NewsResponse

class NewsRepository {

    private val apiKey = "e44ecff320d34e28b16ee72ccb7adc66"

    suspend fun getNews(): NewsResponse =
        RetrofitClient.apiService.getTopHeadlines(apiKey = apiKey)

    suspend fun searchNews(query: String): NewsResponse =
        RetrofitClient.apiService.searchNews(query = query, apiKey = apiKey)
}
