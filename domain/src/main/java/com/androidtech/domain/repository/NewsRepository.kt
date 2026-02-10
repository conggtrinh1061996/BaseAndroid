package com.androidtech.domain.repository

import androidx.paging.PagingData
import com.androidtech.domain.model.news.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    //fun getNews(): Flow<Resource<List<Article>>>
    fun getNewsPaging(category: String, q: String): Flow<PagingData<Article>>
    suspend fun addBookmark(uid: String, article: Article): Result<Unit>
    suspend fun removeBookmark(uid: String, newsId: String): Result<Unit>
    suspend fun getAllBookmarks(uid: String): Flow<Result<List<Article>>>
    fun getSavedBookmarks(): Flow<List<Article>>
}

