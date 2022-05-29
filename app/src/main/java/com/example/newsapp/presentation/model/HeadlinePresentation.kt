package com.example.newsapp.presentation.model

import com.example.newsapp.domain.model.Headline
import java.io.Serializable

data class HeadlinePresentation(
    val title: String?,
    val urlImage: String?,
    val description: String?
):Serializable

fun List<Headline>.toPresentation() =
    map {
        HeadlinePresentation(
            it.title,
            it.urlImage,
            it.description
        )
    }