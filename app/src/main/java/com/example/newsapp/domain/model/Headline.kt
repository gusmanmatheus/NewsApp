package com.example.newsapp.domain.model

import android.util.Log
import com.example.newsapp.data.api.Response
import com.example.newsapp.data.model.HeadLineResponse
import com.example.newsapp.data.model.HeadlineEntity

data class Headline(
    val title: String?,
    val urlImage: String?,
    val description: String?
)

fun List<HeadlineEntity>.toDomain() =
    map {
        Headline(
            it.title,
            it.urlImage,
            it.description
        )
    }

fun Response<HeadLineResponse>.toDomain(): Response<List<Headline>> =
    if (isSuccess) {
        val data = getOrThrow()
        Response.success(data.headlines.toDomain())
    }else
        Response.error(getError())