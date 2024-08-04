package com.example.weathersdk.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Internal use only.
 */
internal data class HourlyForecastResult(
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("country_code")
    val countryCode: String,
    val data: List<HourlyForcastData>,
    @SerializedName("lat")
    val latitude: String,
    @SerializedName("lon")
    val longitude: String,
    @SerializedName("state_code")
    val stateCode: String,
    val timezone: String
)