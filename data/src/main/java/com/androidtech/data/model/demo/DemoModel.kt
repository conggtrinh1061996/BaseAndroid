package com.androidtech.data.model.demo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidtech.domain.model.Demo

@Entity(tableName = "demo")
data class DemoModel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "description") val description: String?
)

fun DemoModel.transform() = Demo(id ?: 0, description ?: "")
