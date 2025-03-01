package com.androidtech.ui.fragment.home

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.extension.None
import com.androidtech.domain.model.Demo
import com.androidtech.domain.use_case.GetDemoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDemoListUseCase: GetDemoListUseCase
): BaseViewModel<HomeViewModel.HomeState>() {

    data class HomeState(
        val demo: Demo? = null,
        val errorMessage: String = "Demo"
    ): UIState

    override fun createInitialState(): HomeState {
        return HomeState()
    }

    fun fetchDemo() {
        viewModelScope.launch {
            getDemoListUseCase(
                None(),
                success = {
                    setState { copy(demo = it) }
                },
                error = {
                    setState { copy(errorMessage = "Data not found. (>-<)") }
                }
            ).collect()
        }
    }
}