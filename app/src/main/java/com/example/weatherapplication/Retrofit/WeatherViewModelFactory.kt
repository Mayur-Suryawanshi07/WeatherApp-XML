package com.example.weatherapplication.Retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.MVVM.Repo
import com.example.weatherapplication.MVVM.WeatherViewModel

class WeatherViewModelFactory(
    private val repo: Repo
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repo) as T
    }
}