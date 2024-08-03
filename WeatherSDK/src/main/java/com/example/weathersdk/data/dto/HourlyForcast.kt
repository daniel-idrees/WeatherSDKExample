package com.example.weathersdk.data.dto

/**
 * Internal use only.
 */
internal data class HourlyForecast (
    val forecasts: List<Forecast>
)

/**
 * Internal use only.
 */
internal data class Forecast (
    val time: String,
    val temperature: String,
    val description: String
)