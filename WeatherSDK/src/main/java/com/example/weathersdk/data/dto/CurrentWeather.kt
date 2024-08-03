package com.example.weathersdk.data.dto

internal data class CurrentWeather(
    val city: String,
    val temperature: String,
    val description: String,
    val localTime: String
)
