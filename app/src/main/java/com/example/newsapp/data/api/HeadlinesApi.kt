package com.example.newsapp.data.api

import com.example.newsapp.BuildConfig.API_KEY
import com.example.newsapp.data.model.HeadlineEntity
import retrofit2.http.GET
import retrofit2.http.Headers

interface HeadlinesApi {
    @Headers("apiKey", API_KEY)
    @GET("/v2/top-headlines")
    suspend fun getHeadlines(): List<HeadlineEntity>
}