package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.WindObject

data class WindModel(
    var speed: Double?,
    var deg: Long?,
    var gust: Double?
)

fun WindModel.transform() = WindObject(speed ?: 0.0, deg ?: 0L, gust ?: 0.0)
