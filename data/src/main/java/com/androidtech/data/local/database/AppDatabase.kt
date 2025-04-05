package com.androidtech.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.user.UserModel

@Database(entities = [DemoModel::class, UserModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        const val DATABASE_NAME = "android_base"
    }
}