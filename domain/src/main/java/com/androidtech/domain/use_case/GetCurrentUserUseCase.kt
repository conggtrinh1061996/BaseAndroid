package com.androidtech.domain.use_case

import com.androidtech.domain.model.user.User
import com.androidtech.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}