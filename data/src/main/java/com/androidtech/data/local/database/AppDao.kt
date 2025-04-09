package com.androidtech.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.user.UserModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserModel>)

    @Query("SELECT * FROM user")
    suspend fun getDemo(): List<DemoModel>

    @Query("DELETE FROM user")
    suspend fun clearAll()
}