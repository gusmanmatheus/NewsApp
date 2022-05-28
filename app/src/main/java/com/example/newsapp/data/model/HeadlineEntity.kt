package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class HeadlineEntity(
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val urlImage: String,
    @SerializedName("description")
    val description: String
)

data class HeadLineResponse(
    @SerializedName("articles")
    val headlines: List<HeadlineEntity>,
    @SerializedName("status")
    val status: String
)