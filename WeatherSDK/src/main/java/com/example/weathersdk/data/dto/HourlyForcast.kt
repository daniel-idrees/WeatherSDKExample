package com.example.weathersdk.data.dto

internal data class HourlyForecast (
    val forecasts: List<Forecast>
)

internal data class Forecast (
    val time: String,
    val temperature: String,
    val description: String
)