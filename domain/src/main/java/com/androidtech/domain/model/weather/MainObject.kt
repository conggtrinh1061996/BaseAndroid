package com.androidtech.domain.model.weather

data class MainObject(
    var temp: Double?,
    var feelsLike: Double?,
    var tempMin: Double?,
    var tempMax: Double?,
    var pressure: Int?,
    var humidity: Int?,
    var seaLevel: Int?,
    var grndLevel: Int?)
