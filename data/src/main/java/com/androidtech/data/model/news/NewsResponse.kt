package com.androidtech.data.model.news

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidtech.domain.model.news.Article
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/*
* data class for news response from NewsApi*/

data class NewsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val url: String,
    val author: String?
)

@RequiresApi(Build.VERSION_CODES.O)
private fun transformTime(time: String): String {
    return try {
        val instant = Instant.parse(time)
        val localDateTime = instant.atZone(ZoneId.systemDefault())

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        localDateTime.format(formatter)
    } catch (e: Exception) {
        time
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun ArticleDto.toEntity(category: String, q: String): ArticleEntity {
    return ArticleEntity(
        id = title + publishedAt,
        title = title,
        description = description,
        imageUrl = urlToImage,
        publishedAt = transformTime(publishedAt),
        url = url,
        category = category,
        q = q,
        author = author
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun ArticleEntity.toDomain(): Article {
    return Article(id, title, description, imageUrl, transformTime(publishedAt), url, author)
}



