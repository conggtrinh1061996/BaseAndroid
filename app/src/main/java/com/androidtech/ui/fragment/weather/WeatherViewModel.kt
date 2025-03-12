package com.androidtech.ui.fragment.weather

import androidx.lifecycle.viewModelScope
import com.androidtech.base.BaseViewModel
import com.androidtech.base.UIState
import com.androidtech.domain.extension.None
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.use_case.GetWeatherListUseCase
import com.androidtech.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherListUseCase: GetWeatherListUseCase
): BaseViewModel<WeatherViewModel.WeatherState>() {

    data class WeatherState (
        val weatherModel: Weather? = null,
        val errorMessage: String = "Weather App"
    ): UIState

    override fun createInitialState(): WeatherState {
        return WeatherState()
    }

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            getWeatherListUseCase(
                cityName,
                success = {
                    setState { copy(weatherModel = it) }
                    Logger.d("${it.name}")
                },
                error = {
                    setState { copy(errorMessage = "Data not found") }
                }
            ).collect()
        }
    }
}