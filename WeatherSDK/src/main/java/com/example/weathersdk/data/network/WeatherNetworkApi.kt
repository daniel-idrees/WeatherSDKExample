package com.example.weathersdk.data.network

import com.example.weathersdk.data.network.model.CurrentWeatherResult
import com.example.weathersdk.data.network.model.HourlyForecastResult
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherNetworkApi {
    @GET("v2.0/current")
    suspend fun getCurrentWeather(
        @Query("city") city: String
    ): CurrentWeatherResult

    @GET("v2.0/forecast/hourly")
    suspend fun getHourlyForecast(
        @Query("city") city: String
    ): HourlyForecastResult
}
