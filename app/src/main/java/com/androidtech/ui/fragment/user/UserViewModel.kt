package com.androidtech.ui.fragment.user

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.model.user.User
import com.androidtech.domain.use_case.DeleteUserUseCase
import com.androidtech.domain.use_case.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.internal.DaggerGenerated
import kotlinx.coroutines.launch
import javax.inject.Inject

@DaggerGenerated
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): BaseViewModel<UserViewModel.UserState>() {
    data class UserState (
        val userList: List<User> = listOf()
    ): UIState

    override fun createInitialState(): UserState {
        return UserState()
    }

    fun getUser() {
        viewModelScope.launch {
            getUserListUseCase().collect {
                setState { copy(userList = it) }
            }
        }
    }

     fun findUserById(id: Int) {
        viewModelScope.launch {
            getUserListUseCase(id).collect {
                setState { copy(userList = it) }
            }
        }
    }

     suspend fun insertUser(user: User) {
        getUserListUseCase.invoke(user)
    }

    suspend fun deleteUser() {
        deleteUserUseCase.invoke()
    }
}