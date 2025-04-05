package com.androidtech.domain.use_case

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.extension.UseCase
import com.androidtech.domain.model.weather.Weather
import com.androidtech.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val locationRepository: LocationRepository
): UseCase<String, Weather>() {
    override fun run(param: String): Flow<Resource<Weather>> = locationRepository.getLocation(param, param)
}