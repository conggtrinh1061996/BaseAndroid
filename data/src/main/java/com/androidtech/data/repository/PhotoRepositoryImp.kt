package com.androidtech.data.repository

import com.androidtech.data.network.ApiService
import com.androidtech.data.model.photo.transform
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.photo.PhotoObject
import com.androidtech.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoRepositoryImp @Inject constructor(
    private val apiService: ApiService
): PhotoRepository {
    override fun getPhoto(): Flow<Resource<List<PhotoObject>>> = flow {
        val response = apiService.getPhoto("https://android-kotlin-fun-mars-server.appspot.com/photos")
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it.map {  photoObjectModel ->
                    photoObjectModel.transform()
                }))

            }

        } else
            emit(Resource.Error(response.message()))
    }

}