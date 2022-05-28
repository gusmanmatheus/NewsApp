package com.example.newsapp.data.api

import com.example.newsapp.BuildConfig.API_KEY
import com.example.newsapp.data.model.HeadLineResponse
import com.example.newsapp.data.model.HeadlineEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HeadlinesApi {
    @GET("/v2/top-headlines/sources")
    suspend fun getHeadlines(): HeadLineResponse
}