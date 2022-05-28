package com.example.newsapp.data.datasources

import com.example.newsapp.data.api.HeadlinesApi
import com.example.newsapp.data.api.Response
import com.example.newsapp.data.api.requestWrapper
import com.example.newsapp.data.model.HeadLineResponse
import com.example.newsapp.data.model.HeadlineEntity

class HeadlinesDataSourcesImpl(private val headlinesApi: HeadlinesApi): HeadlinesDataSources {
    override suspend fun getHeadlines(): Response<HeadLineResponse> {
        return requestWrapper {
            headlinesApi.getHeadlines()
        }
    }
}