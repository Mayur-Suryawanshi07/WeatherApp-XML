package com.example.weatherapplication.MVVM

import com.example.weatherapplication.Retrofit.RetrofitServices

class Repo(
    private val retrofitServices: RetrofitServices
) {

    suspend fun getWeatherDetail(city:String)=retrofitServices.getWeatherDetails(city)
}