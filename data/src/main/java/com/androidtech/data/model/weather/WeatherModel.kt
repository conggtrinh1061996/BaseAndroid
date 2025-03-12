package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.Weather
import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("coord")
    var cord: CoordModel?,
    var weather: List<WeatherObjectModel>?,
    var base: String?,
    var main: MainModel?,
    var visibility: Int?,
    var wind: WindModel?,
    var clouds: CloudsModel?,
    var dt: Long?,
    var sys: SysModel?,
    var timezone: Int?,
    var id: Long?,
    var name: String?,
    var cod: Int?
)

fun WeatherModel.transform() = weather?.map { it.transform() }?.let { listWeather ->
    Weather(
        cord?.transform(),
        listWeather,
        base ?: "",
        main?.transform(),
        visibility ?: 0,
        wind?.transform(),
        clouds?.transform(),
        dt ?: 0L,
        sys?.transform(),
        timezone ?: 0,
        id ?: 0L,
        name ?: "",
        cod ?: 0)
}
