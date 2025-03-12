package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.WeatherObject

data class WeatherObjectModel(
    var id: Int?,
    var main: String?,
    var description: String?,
    var icon: String?
)

fun WeatherObjectModel.transform() = WeatherObject(id ?: 0, main ?: "", description ?: "", icon ?: "")
