package com.androidtech.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidtech.data.model.user.UserEntity
import com.androidtech.data.model.user.toDomain
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.news.Article
import com.androidtech.domain.model.user.User
import com.androidtech.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseDatabase
): AuthRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User not found")
            val userEntity = UserEntity(
                uid = uid,
                name = name,
                email = email,
                createdAt = System.currentTimeMillis() //ServerValue.TIMESTAMP
            )
            firebaseDb.getReference("users")
                .child(uid)
                .setValue(userEntity)
                .await()
            Result.success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            //val uid = result.user?.uid ?: throw Exception("User not found")
            getCurrentUser()

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not found")
            val snapshot = firebaseDb.getReference("users").child(uid).get().await()
            val userEntity = snapshot.getValue(UserEntity::class.java)
            if(userEntity != null) {
                Result.success(userEntity.toDomain())
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }


}