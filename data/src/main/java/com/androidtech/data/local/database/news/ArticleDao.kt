package com.androidtech.data.local.database.news

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtech.data.model.news.ArticleEntity
import kotlinx.coroutines.flow.Flow

/*
* Dao for news show in newsFragment*/
@Dao
interface ArticleDao {
    @Query("""
        SELECT * FROM articles 
        WHERE category = :category 
        AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY publishedAt DESC""")
    fun pagingSource(category: String, query: String): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE category = :category")
    suspend fun clearArticles(category: String)

}