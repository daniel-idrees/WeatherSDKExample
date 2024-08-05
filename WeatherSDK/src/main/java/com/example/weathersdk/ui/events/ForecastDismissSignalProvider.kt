package com.example.weathersdk.ui.events

import com.example.weathersdk.WeatherSdkInstanceProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Internal use only.
 */
@Singleton
internal class ForecastDismissSignalProvider @Inject constructor(
    private val weatherSdkInstanceProvider: WeatherSdkInstanceProvider
) {
    fun get() = weatherSdkInstanceProvider.get().forecastDismissSignal
}