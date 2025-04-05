package com.androidtech.domain.use_case

import com.androidtech.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.deleteAllUser()
}