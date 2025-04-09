package com.example.weatherapplication.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object{
         var retrofitServices: RetrofitServices?=null
        fun getInstance(): RetrofitServices {
            if(retrofitServices ==null){
                retrofitServices =Retrofit.Builder()
                    .baseUrl("https://api.weatherapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitServices::class.java)

            }
            return retrofitServices!!

        }
    }
}