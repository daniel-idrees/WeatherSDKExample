package com.example.weathersdk.data.repository

import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import kotlinx.coroutines.flow.Flow

/**
 * Internal use only.
 */
internal interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): Flow<WeatherResult<CurrentWeather>>
    suspend fun getHourlyForecast(city: String): Flow<WeatherResult<HourlyForecast>>
}