package com.example.newsapp.domain.model

import com.example.newsapp.data.api.Response
import com.example.newsapp.data.model.HeadlineEntity

data class Headline(
    val title: String,
    val urlImage: String,
    val description: String
)

fun List<HeadlineEntity>.toDomain() =
    map {
        Headline(
            it.title,
            it.urlImage,
            it.description
        )
    }

fun Response<List<HeadlineEntity>>.toDomain(): Response<List<Headline>> =
    if (isSuccess)
        Response.success(getOrThrow().toDomain())
    else
        Response.error(getError())