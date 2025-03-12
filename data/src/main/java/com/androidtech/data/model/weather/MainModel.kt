package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.MainObject
import com.google.gson.annotations.SerializedName

data class MainModel(
    var temp: Double?,
    @SerializedName("feels_like")
    var feelsLike: Double?,
    @SerializedName("temp_min")
    var tempMin: Double?,
    @SerializedName("temp_max")
    var tempMax: Double?,
    var pressure: Int?,
    var humidity: Int?,
    @SerializedName("sea_level")
    var seaLevel: Int?,
    @SerializedName("grnd_level")
    var grndLevel: Int?
)

fun MainModel.transform() = MainObject(temp ?: 0.0, feelsLike ?: 0.0, tempMin  ?: 0.0,
    tempMax ?: 0.0, pressure ?: 0, humidity ?: 0, seaLevel ?: 0, grndLevel ?: 0)
