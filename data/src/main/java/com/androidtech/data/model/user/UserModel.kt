package com.androidtech.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidtech.domain.model.user.User

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true) var uid: Int?,
    @ColumnInfo(name = "name") var name: String?
)

fun UserModel.transform() = User(uid ?: 0, name ?: "")
