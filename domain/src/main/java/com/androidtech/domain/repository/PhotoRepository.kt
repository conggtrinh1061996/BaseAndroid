package com.androidtech.domain.repository

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.photo.PhotoObject
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhoto(): Flow<Resource<List<PhotoObject>>>
}