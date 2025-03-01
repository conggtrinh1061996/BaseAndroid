package com.androidtech.domain.repository

import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.Demo
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getDemo(): Flow<Resource<Demo>>
}