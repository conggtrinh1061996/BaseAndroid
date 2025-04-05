package com.androidtech.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.user.UserModel

@Dao
interface AppDao {
    @Query("SELECT * FROM demo")
    suspend fun getDemo(): List<DemoModel>

    @Query("SELECT * FROM user")
    suspend fun getUser(): List<UserModel>

    @Query("SELECT * FROM user WHERE uid LIKE :id")
    suspend fun findUserById(id: Int): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: UserModel)

    @Delete
    suspend fun deleteUser(user: UserModel)

    @Query("DELETE FROM user")
    suspend fun deleteAllUser()
}