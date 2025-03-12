package com.androidtech.domain.repository

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(city: String): Flow<Resource<Weather>>
}