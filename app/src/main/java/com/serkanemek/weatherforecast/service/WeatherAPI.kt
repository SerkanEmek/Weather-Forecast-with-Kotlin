package com.serkanemek.weatherforecast.service

import com.serkanemek.weatherforecast.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {


    //http://api.weatherapi.com/v1/current.json?key=7adcd30313e64d1bb2a112547231901&q=istanbul

    @GET("current.json?key=7adcd30313e64d1bb2a112547231901&q=istanbul")
    fun getData(): Single<WeatherModel>
}