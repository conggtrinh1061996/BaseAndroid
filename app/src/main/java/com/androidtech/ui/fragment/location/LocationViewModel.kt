package com.androidtech.ui.fragment.location

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.use_case.GetLocationListUseCase
import com.androidtech.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationListUseCase: GetLocationListUseCase
): BaseViewModel<LocationViewModel.LocationState>(){
    data class LocationState (
        val locationModel: Weather? = null,
        val errorMessage: String = "Location app"
    ): UIState

    override fun createInitialState(): LocationState {
        return LocationState()
    }

    fun fetchLocation(lon: String, lat: String) {
        viewModelScope.launch {
            locationListUseCase(
                Pair(lon, lat).toString(),
                success = {
                    setState { copy(locationModel = it) }
                    Logger.d("location: ${it.name}")
                },
                error = {
                    setState { copy(errorMessage = "Data not found") }
                }
            )
        }
    }
}