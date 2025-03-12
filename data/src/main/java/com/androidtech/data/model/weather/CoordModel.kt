package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.CoordObject

data class CoordModel(
    var lon: Double?,
    var lat: Double?
)

fun CoordModel.transform() = CoordObject(lon ?: 0.0, lat ?: 0.0)
