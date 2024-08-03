package com.example.weathersdk.data.repository

import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import com.example.weathersdk.data.dto.mapper.toCurrentWeather
import com.example.weathersdk.data.dto.mapper.toHourlyForecast
import com.example.weathersdk.data.network.WeatherNetworkApi
import com.example.weathersdk.data.runSuspendCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException
import javax.inject.Inject

/**
 * Internal use only.
 */
internal class WeatherNetworkRepository @Inject constructor(
    private val networkApi: WeatherNetworkApi,
) : WeatherRepository {
    override suspend fun getCurrentWeather(city: String): Flow<WeatherResult<CurrentWeather>> =
        runSuspendCatching {
            val response = networkApi.getCurrentWeather(city).toCurrentWeather()
            flowOf(WeatherResult.Success(response))
        }.onFailure {
            // log
        }.getOrElse {
            flowOf(getErrorResult(it))
        }

    override suspend fun getHourlyForecast(city: String): Flow<WeatherResult<HourlyForecast>> =
        runSuspendCatching {
            val response = networkApi.getHourlyForecast(city).toHourlyForecast()
            flowOf(WeatherResult.Success(response))
        }.onFailure {
            // log
        }.getOrElse {
            flowOf(getErrorResult(it))
        }

    private fun getErrorResult(throwable: Throwable): WeatherResult<Nothing> {
        return when (throwable) {
            is IOException -> WeatherResult.NoInternetConnection
            else -> WeatherResult.Error
        }
    }
}