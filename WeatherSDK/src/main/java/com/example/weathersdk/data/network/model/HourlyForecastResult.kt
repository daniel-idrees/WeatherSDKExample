package com.example.weathersdk.data.network.model

import com.squareup.moshi.Json

internal data class HourlyForecastResult(
    @field:Json(name = "city_name")
    val cityName: String,
    @field:Json(name = "country_code")
    val countryCode: String,
    val data: List<HourlyForcastData>,
    @field:Json(name = "lat")
    val latitude: String,
    @field:Json(name = "lon")
    val longitude: String,
    @field:Json(name = "state_code")
    val stateCode: String,
    val timezone: String
)