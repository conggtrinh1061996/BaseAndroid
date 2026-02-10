package com.androidtech.data.model.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String,
    val category: String,
    val q: String,
    val author: String?
)

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val articleId: String,
    val prevKey: Int?,
    val nextKey: Int?
)