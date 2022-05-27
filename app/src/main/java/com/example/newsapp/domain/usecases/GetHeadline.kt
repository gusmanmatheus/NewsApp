package com.example.newsapp.domain.usecases

import com.example.newsapp.data.HeadlinesRepository
import com.example.newsapp.data.api.Response
import com.example.newsapp.domain.model.Headline
import com.example.newsapp.domain.model.toDomain

class GetHeadline(private val repository: HeadlinesRepository) : GetHeadlineUseCase {
    override suspend fun invoke(): Response<List<Headline>> {
        return repository.getHeadlines().toDomain()
    }
}