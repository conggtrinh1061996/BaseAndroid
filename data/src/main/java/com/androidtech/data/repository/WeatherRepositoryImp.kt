package com.androidtech.data.repository

import com.androidtech.data.model.weather.transform
import com.androidtech.data.network.ApiService
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val apiService: ApiService
): WeatherRepository {
    override fun getWeather(city: String): Flow<Resource<Weather>> = flow {
        val response = apiService.getWeather(city, "5b14bf48d1725eec39f8fc50ce94680c", "metric")
        if (response.isSuccessful) {
            response.body()?.let { weatherModel ->
                weatherModel.transform()?.let { weather ->
                    emit(Resource.Success(weather))
                }
            }

        } else
            emit(Resource.Error(response.message()))
    }
}