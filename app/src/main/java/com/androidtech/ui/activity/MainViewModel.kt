package com.androidtech.ui.activity

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.extension.None
import com.androidtech.domain.model.Demo
import com.androidtech.domain.use_case.GetDemoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDemoListUseCase: GetDemoListUseCase
): BaseViewModel<MainViewModel.MainState>() {

    data class MainState(
        val demo: Demo? = null,
        val exception: Exception? = null
    ): UIState

    override fun createInitialState(): MainState {
        return MainState()
    }

    fun getDemo() {
        viewModelScope.launch {
            getDemoListUseCase(
                None(),
                success = { demo: Demo ->
                    setState { copy(demo = demo) }
                },
                error = { throwable ->
                    setState { copy(exception = Exception(throwable.message)) }
                }
            )
        }
    }
}