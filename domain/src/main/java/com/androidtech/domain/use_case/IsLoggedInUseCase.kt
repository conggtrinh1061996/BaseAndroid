package com.androidtech.domain.use_case

import com.androidtech.domain.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}