package com.example.weathersdk.data.network.model

/**
 * Internal use only.
 */
internal data class Weather(
    val code: Int,
    val description: String,
    val icon: String
)