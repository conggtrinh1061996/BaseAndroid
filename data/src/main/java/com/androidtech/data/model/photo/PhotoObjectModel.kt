package com.androidtech.data.model.photo

import com.androidtech.domain.model.photo.PhotoObject
import com.google.gson.annotations.SerializedName

data class PhotoObjectModel(
    val id: String? = null,
    @SerializedName("img_src")
    val imgSrc: String? = null
)

fun PhotoObjectModel.transform() = PhotoObject(id ?: "", imgSrc ?: "")