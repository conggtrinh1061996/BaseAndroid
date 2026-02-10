package com.androidtech.domain.model.news


data class Article(
    val id: String="",
    val title: String="",
    val description: String?="",
    val imageUrl: String?="",
    val publishedAt: String="",
    val url: String="",
    val author: String?=""
)