package com.example.newsapp.presentation.model

import com.example.newsapp.domain.model.Headline

data class HeadlinePresentation(
    val title: String?,
    val urlImage: String?,
    val description: String?
)

fun List<Headline>.toPresentation() =
    map {
        HeadlinePresentation(
            it.title,
            it.urlImage,
            it.description
        )
    }