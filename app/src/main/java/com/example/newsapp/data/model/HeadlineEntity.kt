package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class HeadlineEntity(
    @SerializedName("name")
    val title: String,
    @SerializedName("url")
    val urlImage: String,
    @SerializedName("description")
    val description: String
)

data class HeadLineResponse(
    @SerializedName("sources")
    val headlines: List<HeadlineEntity>,
    @SerializedName("status")
    val status: String
)