package com.example.weathersdk.data.network.model

internal data class CurrentWeatherResult(
    val count: Int,
    val data: List<CurrentWeatherData>
)