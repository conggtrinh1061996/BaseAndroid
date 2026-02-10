package com.androidtech.data.model.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidtech.domain.model.user.User
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class UserEntity (
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = 0L
) {
    constructor() : this("", "", "", 0L)
}

@RequiresApi(Build.VERSION_CODES.O)
fun UserEntity.toDomain(): User {
    return User(
        uid = this.uid,
        name = this.name,
        email = this.email,
        displayDate = this.createdAt.formatToDateString("dd MMMM, yyyy")
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.formatToDateString(pattern: String = "dd/MM/yyyy HH:mm"): String {
    return try {
        val instant = Instant.ofEpochMilli(this)
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            .withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        "N/A"
    }
}