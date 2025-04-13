package com.androidtech.data.network

import com.androidtech.data.model.demo.DemoModel
import com.androidtech.data.model.weather.WeatherModel
import com.androidtech.data.model.photo.PhotoObjectModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("your_api_end_point")
    suspend fun getAllDemo(): Response<DemoModel>

    @GET("data/2.5/weather")
    suspend fun getWeather(@Query("q") cityName: String, @Query("appid") id: String,
                           @Query("units") units: String): Response<WeatherModel>

    @GET("data/2.5/weather")
    suspend fun getLocationWeather(
        @Query("lon") longitude: String,
        @Query("lat") latitude: String,
        @Query("appid") id: String,
        @Query("units") units: String
    ): Response<WeatherModel>

    @GET
    suspend fun getPhoto(@Url url: String): Response<List<PhotoObjectModel>>
}