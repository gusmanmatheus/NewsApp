package com.example.newsapp.domain.usecases

import com.example.newsapp.data.api.Response
import com.example.newsapp.domain.model.Headline

interface GetHeadlineUseCase {
    suspend operator fun invoke(): Response<List<Headline>>
}