package com.example.weathersdk.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Internal use only.
 */
internal data class CurrentWeatherResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val data: List<CurrentWeatherData>
)