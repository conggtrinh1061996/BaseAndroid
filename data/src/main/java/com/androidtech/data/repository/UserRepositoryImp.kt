package com.androidtech.data.repository

import com.androidtech.data.local.database.AppDao
import com.androidtech.data.model.user.UserModel
import com.androidtech.data.model.user.transform
import com.androidtech.domain.model.user.User
import com.androidtech.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private var userDao: AppDao) : UserRepository {
    override fun getUser(): Flow<List<User>> = flow {
        val users = userDao.getUser().map { userModel ->
            userModel.transform()
        }

        emit(users)
    }.flowOn(Dispatchers.IO)

    override suspend fun insertUser(user: User) {
        userDao.insertUser(UserModel(user.uid, user.name))
    }

    override suspend fun deleteAllUser() {
        userDao.deleteAllUser()
    }

    override suspend fun deleteByUser(user: User) {
        userDao.deleteUser(UserModel(user.uid, user.name))
    }

    override fun findUserById(id: Int): Flow<List<User>> = flow {
        val users = userDao.findUserById(id).map { userModel ->
            userModel.transform()
        }

        emit(users)
    }.flowOn(Dispatchers.IO)
}