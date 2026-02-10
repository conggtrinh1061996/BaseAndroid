package com.androidtech.data.network

import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.news.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("your_api_end_point")
    suspend fun getAllDemo(): Response<DemoModel>


    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = "us",
        @Query("category") category: String?,
        @Query("q") q: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsResponse
}