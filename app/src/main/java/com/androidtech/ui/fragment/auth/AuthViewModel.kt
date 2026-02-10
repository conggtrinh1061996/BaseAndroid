package com.androidtech.ui.fragment.auth

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.model.user.User
import com.androidtech.domain.use_case.LoginUseCase
import com.androidtech.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
): BaseViewModel<AuthViewModel.AuthState>() {
    override fun createInitialState(): AuthState {
        return AuthState()
    }

    data class AuthState(
        val user: User? = null,
        val errorMessage: String? = null
    ): UIState
    fun onRegisterClicked(email: String, password: String, name: String) {
        viewModelScope.launch {
            val result = registerUseCase(email, password, name)
            if(result.isSuccess) setState { copy(user = result.getOrNull()) }
            else setState { copy(errorMessage = result.exceptionOrNull()?.message) }
        }
    }
    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase(email, password)
            if(result.isSuccess) setState { copy(user = result.getOrNull()) }
            else setState { copy(errorMessage = result.exceptionOrNull()?.message) }
        }
    }

}