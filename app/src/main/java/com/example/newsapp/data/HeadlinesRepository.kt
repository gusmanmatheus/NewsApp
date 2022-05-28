package com.example.newsapp.data

import com.example.newsapp.data.api.Response
import com.example.newsapp.data.model.HeadLineResponse
import com.example.newsapp.data.model.HeadlineEntity

interface HeadlinesRepository {
    suspend fun getHeadlines(): Response<HeadLineResponse>
}