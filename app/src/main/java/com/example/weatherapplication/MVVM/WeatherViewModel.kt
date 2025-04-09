package com.example.weatherapplication.MVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.WeatherResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repo:Repo
): ViewModel (){
    val weatherDetailLiveData=MutableLiveData<WeatherResponseModel>()
    val isloading=MutableLiveData<Boolean>(false)
    fun getWeatherDetail(city:String){
        viewModelScope.launch(Dispatchers.IO) {

            isloading.postValue(true)
            val response = repo.getWeatherDetail(city)
            if(response.isSuccessful){
                weatherDetailLiveData.postValue(response.body())
                isloading.postValue(false)
            }
        }

    }
}