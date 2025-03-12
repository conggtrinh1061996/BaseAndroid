package com.androidtech.domain.model.weather

data class Weather(
    var coord: CoordObject?,
    var weather: List<WeatherObject>?,
    var base: String?,
    var main: MainObject?,
    var visibility: Int?,
    var wind: WindObject?,
    var clouds: CloudsObject?,
    var dt: Long?,
    var sys: SysObject?,
    var timezone: Int?,
    var id: Long?,
    var name: String?,
    var cod: Int?
)
