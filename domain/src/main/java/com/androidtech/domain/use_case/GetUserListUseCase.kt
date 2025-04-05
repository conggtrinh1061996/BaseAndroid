package com.androidtech.domain.use_case

import com.androidtech.domain.model.user.User
import com.androidtech.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {
    operator fun invoke(): Flow<List<User>> = userRepository.getUser()

    operator fun invoke(id: Int): Flow<List<User>> = userRepository.findUserById(id)

    suspend operator fun invoke(user: User) = userRepository.insertUser(user)
    }
