package com.androidtech.ui.fragment.mars_photo

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.extension.None
import com.androidtech.domain.model.photo.PhotoObject
import com.androidtech.domain.use_case.PhotoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotoListUseCase: PhotoListUseCase
): BaseViewModel<PhotoViewModel.PhotoState>() {
    data class PhotoState(
        val photoList: List<PhotoObject>? = null,
        val errorMessage: String = "Photo app"
    ): UIState

    override fun createInitialState(): PhotoState {
        return PhotoState()
    }

    fun fetchPhoto() {
        viewModelScope.launch {
            getPhotoListUseCase(
                success = { setState { copy(photoList = it) } },
                error = { setState { copy(errorMessage = "Data not found") } },
                param = None()
            ).collect()
        }
    }
}