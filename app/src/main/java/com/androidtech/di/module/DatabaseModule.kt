package com.androidtech.di.module

import android.content.Context
import androidx.room.PrimaryKey
import androidx.room.Room
import com.androidtech.app.MainApplication
import com.androidtech.data.local.database.AppDao
import com.androidtech.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase): AppDao = appDatabase.getAppDao()

    @Provides
    fun application(): Context = MainApplication.getInstance()
}