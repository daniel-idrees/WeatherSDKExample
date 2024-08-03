package com.example.weathersdk.data.dto

/**
 * Internal use only.
 */
internal sealed interface WeatherResult<out T> {
    data class Success<T>(val data: T) : WeatherResult<T>
    data object Error : WeatherResult<Nothing>
    data object NoInternetConnection : WeatherResult<Nothing>
}