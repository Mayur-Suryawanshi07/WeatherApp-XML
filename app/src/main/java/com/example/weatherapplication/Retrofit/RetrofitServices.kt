package com.example.weatherapplication.Retrofit

import com.example.weatherapplication.data.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitServices {
    //https://api.weatherapi.com/v1/current.json?key=3bc60a37f9634015b48204756241011&q=Delhi my http request

    @GET("/v1/current.json?key=3bc60a37f9634015b48204756241011&aqi=yes")
    suspend fun getWeatherDetails(
        @Query("q") city:String
    ):Response<WeatherResponseModel>
}