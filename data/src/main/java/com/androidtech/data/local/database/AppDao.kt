package com.androidtech.data.local.database

import androidx.room.Dao
import androidx.room.Query
import com.androidtech.data.model.demo.DemoModel

@Dao
interface AppDao {

    @Query("SELECT * FROM demo")
    suspend fun getDemo(): List<DemoModel>
}