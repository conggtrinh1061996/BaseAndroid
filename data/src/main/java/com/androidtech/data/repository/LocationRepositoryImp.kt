package com.androidtech.data.repository

import com.androidtech.data.model.weather.transform
import com.androidtech.data.network.ApiService
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.logging.Logger
import javax.inject.Inject

class LocationRepositoryImp @Inject constructor(
    private val apiService: ApiService
): LocationRepository {
    override fun getLocation(lon: String, lat: String): Flow<Resource<Weather>> = flow {
        val response = apiService.getLocationWeather(lon, lat, "5b14bf48d1725eec39f8fc50ce94680c", "metric")
        if (response.isSuccessful) {
            response.body()?.let {  weatherModel ->
                weatherModel.transform()?.let {  weather ->
                    emit(Resource.Success(weather))
                }
            }

        } else
            emit(Resource.Error(response.message()))
    }

}