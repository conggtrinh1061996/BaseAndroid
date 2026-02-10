package com.androidtech.di.module

import android.content.Context
import androidx.room.Room
import com.androidtech.data.local.database.AppDatabase
import com.androidtech.data.local.database.news.ArticleDao
import com.androidtech.data.local.database.news.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao = appDatabase.getNewsDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(appDatabase: AppDatabase): RemoteKeysDao = appDatabase.remoteKeyDao()

}