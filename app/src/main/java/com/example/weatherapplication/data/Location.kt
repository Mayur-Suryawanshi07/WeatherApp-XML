package com.example.weatherapplication.data

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)