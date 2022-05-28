package com.example.newsapp.data.api

import com.example.newsapp.data.model.HeadLineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlinesApi {
    @GET("/v2/top-headlines")
    suspend fun getHeadlines(@Query("sources") sources:String = "bbc-news"): HeadLineResponse
}