package com.androidtech.data.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidtech.domain.model.news.Article

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String = "",
    val title: String? = "",
    val description: String? = "",
    val imageUrl: String? = "",
    val publishedAt: String? = "",
    val url: String? = "",
    val author: String? = "",
)

data class BookmarkDto(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val url: String,
    val author: String?
)

fun Article.toEntity() = BookmarkEntity(
    url = url,
    title = title,
    description = description,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    author = author,
)

fun BookmarkEntity.toDomain(): Article {
    return Article(id, title="", description, imageUrl, publishedAt="", url="", author)
}
