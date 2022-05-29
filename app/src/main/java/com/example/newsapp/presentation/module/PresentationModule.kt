package com.example.newsapp.presentation.module

import com.example.newsapp.presentation.headlinelist.MainViewModel
import org.koin.dsl.module

val presentationModule = module {
    single { MainViewModel(getHeadlineUseCase = get()) }
}