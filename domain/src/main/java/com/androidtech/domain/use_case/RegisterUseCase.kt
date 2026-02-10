package com.androidtech.domain.use_case

import com.androidtech.domain.model.user.User
import com.androidtech.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<User> {
        return authRepository.register(email, password, name)
    }
}