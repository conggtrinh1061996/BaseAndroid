package com.androidtech.domain.repository

import com.androidtech.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<List<User>>

    suspend fun insertUser(user: User)

    suspend fun deleteAllUser()

    suspend fun deleteByUser(user: User)

    fun findUserById(id: Int): Flow<List<User>>
}