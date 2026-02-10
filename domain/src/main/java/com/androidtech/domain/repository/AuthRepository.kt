package com.androidtech.domain.repository

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email:String, password:String, name:String): Result<User>
    suspend fun login(email:String, password:String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): Result<User>
    fun isUserLoggedIn(): Boolean

}