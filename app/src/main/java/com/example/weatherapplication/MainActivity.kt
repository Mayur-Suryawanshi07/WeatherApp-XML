package com.example.weatherapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapplication.MVVM.Repo
import com.example.weatherapplication.MVVM.WeatherViewModel
import com.example.weatherapplication.Retrofit.RetrofitBuilder
import com.example.weatherapplication.Retrofit.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var repo: Repo
    private lateinit var weatherViewModelFactory: WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var loader: ProgressBar

    private lateinit var edtCityName:EditText
    private lateinit var btnGetWeather:Button
    private lateinit var imgWeather:ImageView
    private lateinit var textWeather:TextView
    private lateinit var textCityStateName:TextView
    private lateinit var textwindspeed:TextView
    private lateinit var currentUV:TextView
    private lateinit var currentaqi:TextView
    private lateinit var wheathertype:TextView


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        btnGetWeather.setOnClickListener {
            weatherViewModel.getWeatherDetail(edtCityName.text.toString())
        }

        weatherViewModel.weatherDetailLiveData.observe( this) {
            val c="C"
            val kph="Kph"
            val currentWindSpedd=it.current.wind_kph
            val currentWeatherType = it.current.condition.text
            val currentTempInC = it.current.temp_c
            val currentuv=it.current.uv
            val aqipm2=it.current.air_quality.pm2_5
            val aqipm10=it.current.air_quality.pm10
            val aqico=it.current.air_quality.co
            //val daynight=it.current.is_day

            textWeather.text= "Tempreature : $currentTempInC $c"

            val imageLink = "https:${it.current.condition.icon}"
            Glide.with( this)
            .load(imageLink)
                .into(imgWeather)

            val cityName = it.location.name
            val state = it.location.region
            val cityAndState = "$cityName, $state"
            textCityStateName.text=cityAndState

            val windspeed=" Wind Speed :$currentWindSpedd $kph"
            textwindspeed.text=windspeed





            val uv="Current Uv : $currentuv" + " " + getUVSafetyLevel(currentuv)
            currentUV.text=uv

            val airquality= categorizeAQI(aqipm2.toInt())
            currentaqi.text=airquality

            val wtype="$currentWeatherType"
            wheathertype.text=wtype

        }


        weatherViewModel.isloading.observe( this) {
            if (it) {
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }
        }
    }


    private fun init() {
        repo = Repo(RetrofitBuilder.getInstance())
        weatherViewModelFactory = WeatherViewModelFactory(repo)
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory)[WeatherViewModel::class.java]
        loader = findViewById(R.id.loader)


        edtCityName=findViewById(R.id.etd_weather)
        btnGetWeather=findViewById(R.id.btn_weather)
        imgWeather=findViewById(R.id.img_weather)
        textWeather=findViewById(R.id.txt_weather)
        textCityStateName=findViewById(R.id.txt_cityname)
        textwindspeed=findViewById(R.id.txt_currentwindspeed)
        currentUV=findViewById(R.id.txt_uv)
        currentaqi=findViewById(R.id.txt_aqi)
        wheathertype=findViewById(R.id.txt_Weathertype)

    }


}
fun categorizeAQI(aqi: Int): String {
    return if (aqi <= 50) {
        "AQI: $aqi (Low)"
    } else if (aqi in 51..100) {
        "AQI: $aqi (Moderate)"
    } else if (aqi in 101..150) {
        "AQI: $aqi (High) "
    } else {
        "AQI: $aqi (Very High) "
    }
}
fun getUVSafetyLevel(uvIndex: Double): String {
    return when {
        uvIndex in 0.0..2.9 -> "Low (Safe)"
        uvIndex in 3.0..5.9 -> "Moderate (Safe with Precaution)"
        uvIndex in 6.0..7.9 -> "High (Not Safe - Use Protection)"
        uvIndex in 8.0..10.9 -> "Very High (Not Safe - Avoid Sun)"
        uvIndex >= 11.0 -> "Extreme (Not Safe - Stay Indoors)"
        else -> "Invalid UV Index"
    }
}






