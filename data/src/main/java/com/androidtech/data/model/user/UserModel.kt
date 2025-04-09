package com.androidtech.data.model.user

import androidx.room.Entity
import com.androidtech.domain.model.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "user")
@JsonClass(generateAdapter = true)
data class UserModel(
    @Json(name = "id") val id: Int?,
    @Json(name = "login") val login: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "url") val url: String?
)

fun UserModel.transform(): User =
    User(
        id = id ?: 0,
        login ?: "",
        avatarUrl ?: "",
        url ?: ""
    )


