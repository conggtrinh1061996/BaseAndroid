package com.androidtech.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidtech.data.local.database.news.ArticleDao
import com.androidtech.data.local.database.news.BookmarkDao
import com.androidtech.data.local.database.news.RemoteKeysDao
import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.news.ArticleEntity
import com.androidtech.data.model.news.BookmarkEntity
import com.androidtech.data.model.news.RemoteKey

//@Database(entities = [DemoModel::class], version = 1, exportSchema = false)
@Database(entities = [ArticleEntity::class, RemoteKey::class, BookmarkEntity::class], version = 1, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {

    //abstract fun getAppDao(): AppDao
    abstract fun getNewsDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeysDao
    abstract fun bookmarkDao(): BookmarkDao


    companion object {
        const val DATABASE_NAME = "android_base"
    }
}