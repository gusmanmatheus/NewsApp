package com.example.newsapp.data
import com.example.newsapp.data.api.Response
import com.example.newsapp.data.datasources.HeadlinesDataSources
import com.example.newsapp.data.model.HeadlineEntity

class HeadlinesRepositoryImpl(private val dataSources: HeadlinesDataSources) :
    HeadlinesRepository {
    override suspend fun getHeadlines(): Response<List<HeadlineEntity>> {
        return dataSources.getHeadlines()
    }
}