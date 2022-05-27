package com.example.newsapp.data.datasources

import com.example.newsapp.data.api.Response
import com.example.newsapp.data.model.HeadlineEntity

interface HeadlinesDataSources {
   suspend fun getHeadlines(): Response<List<HeadlineEntity>>
}