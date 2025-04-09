package com.androidtech.data.network

import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.user.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("your_api_end_point")
    suspend fun getAllDemo(): Response<DemoModel>

    @GET
    suspend fun getUsersByPage(
        @Url url: String,
        @Query("per_page") perPage: Int,
        @Query("since") size: Int
    ): Response<List<UserModel>>
}