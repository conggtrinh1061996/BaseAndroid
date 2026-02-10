package com.androidtech.data.local.database.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtech.data.model.news.RemoteKey

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE articleId = :id")
    suspend fun remoteKeyById(id: String): RemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKey>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
