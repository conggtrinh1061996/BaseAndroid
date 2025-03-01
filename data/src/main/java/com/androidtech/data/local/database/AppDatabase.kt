package com.androidtech.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidtech.data.model.demo.DemoModel

@Database(entities = [DemoModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        const val DATABASE_NAME = "android_base"
    }
}