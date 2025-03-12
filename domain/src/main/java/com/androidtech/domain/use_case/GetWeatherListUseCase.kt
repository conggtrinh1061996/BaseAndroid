package com.androidtech.domain.use_case

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.extension.UseCase
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
):UseCase<String, Weather>() {
    override fun run(param: String): Flow<Resource<Weather>> = weatherRepository.getWeather(param)
}