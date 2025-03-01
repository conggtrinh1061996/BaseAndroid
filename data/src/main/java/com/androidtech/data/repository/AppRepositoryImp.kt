package com.androidtech.data.repository

import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.demo.transform
import com.androidtech.data.network.ApiService
import com.androidtech.domain.extension.Resource
import com.androidtech.domain.model.Demo
import com.androidtech.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(
    private val apiService: ApiService
): AppRepository {

    override fun getDemo(): Flow<Resource<Demo>> = flow {

        // Real method to call API
        /*val response = apiService.getAllDemo()
        if (response.isSuccessful) {
            response.body()?.let { demoModel ->
                emit(Resource.Success(demoModel.transform()))
            }
        } else {
            emit(Resource.Error(response.message()))
        }*/

        // Fake data to run
        emit(Resource.Success(
            DemoModel(
                1,"Test Data demo"
            ).transform()
        ))
    }
}