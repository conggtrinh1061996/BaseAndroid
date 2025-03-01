package com.androidtech.data.model.demo

import com.androidtech.domain.model.Demo

data class DemoModel(
    val id: Int?,
    val description: String?
)

fun DemoModel.transform() = Demo(id ?: 0, description ?: "")
