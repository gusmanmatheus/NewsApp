package com.example.newsapp.domain.module

import com.example.newsapp.domain.usecases.GetHeadline
import com.example.newsapp.domain.usecases.GetHeadlineUseCase
import org.koin.dsl.module

val domainModules = module {
    single<GetHeadlineUseCase> {
        GetHeadline(repository = get())
    }
}