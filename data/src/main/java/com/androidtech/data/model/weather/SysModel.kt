package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.SysObject

data class SysModel(
    var type: Int?,
    var id: Long?,
    var country: String?,
    var sunrise: Long?,
    var sunset: Long?
)

fun SysModel.transform() = SysObject(type ?: 0, id ?: 0L, country ?: "", sunrise ?: 0L, sunset ?: 0L)
