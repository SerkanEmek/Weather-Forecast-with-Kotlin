package com.serkanemek.weatherforecast.service

import com.serkanemek.weatherforecast.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class WeatherAPIService {

    //http://api.weatherapi.com/v1/current.json?key=7adcd30313e64d1bb2a112547231901&q=istanbul

    private val BASE_URL = "http://api.weatherapi.com/v1/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getData() : Single<WeatherModel> {
        return api.getData()
    }
}

