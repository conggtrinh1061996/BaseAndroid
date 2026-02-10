package com.androidtech.data.local.database.news

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtech.data.model.news.BookmarkEntity
import kotlinx.coroutines.flow.Flow

/*Dao for bookmark show in bookmarkFragment*/
@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookmark: BookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookmark(bookmark: List<BookmarkEntity>)



    @Query("SELECT * FROM bookmarks")
    fun getSavedBookmark(): Flow<List<BookmarkEntity>>

    @Query("DELETE FROM bookmarks WHERE url = :url")
    suspend fun deleteByUrl(url: String)

    @Query("DELETE FROM bookmarks WHERE url NOT IN (:urls)")
    suspend fun deleteNotIn(urls: List<String>)

    @Query("SELECT EXISTS (SELECT * FROM bookmarks WHERE id = :id )")
    suspend fun isBookmarked(id: String): Boolean
}