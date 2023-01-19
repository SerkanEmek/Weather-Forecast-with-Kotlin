package com.serkanemek.weatherforecast.model


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)