package com.androidtech.data.network

import com.androidtech.data.model.demo.DemoModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("your_api_end_point")
    suspend fun getAllDemo(): Response<DemoModel>

}