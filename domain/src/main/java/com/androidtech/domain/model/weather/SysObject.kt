package com.androidtech.domain.model.weather

data class SysObject(
    var type: Int?,
    var id: Long?,
    var country: String?,
    var sunrise: Long?,
    var sunset: Long?
)
