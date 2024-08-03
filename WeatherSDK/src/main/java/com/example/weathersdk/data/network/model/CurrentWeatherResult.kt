package com.example.weathersdk.data.network.model

/**
 * Internal use only.
 */
internal data class CurrentWeatherResult(
    val count: Int,
    val data: List<CurrentWeatherData>
)