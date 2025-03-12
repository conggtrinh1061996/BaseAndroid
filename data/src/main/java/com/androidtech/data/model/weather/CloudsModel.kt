package com.androidtech.data.model.weather

import com.androidtech.domain.model.weather.CloudsObject

data class CloudsModel(var all: Int?)

fun CloudsModel.transform() = CloudsObject(all ?: 0)
