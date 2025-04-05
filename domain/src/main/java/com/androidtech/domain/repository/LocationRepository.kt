package com.androidtech.domain.repository

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.weather.Weather
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    fun getLocation(lon: String, lat: String): Flow<Resource<Weather>>
}