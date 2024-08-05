package com.example.weathersdk.ui.events

import com.example.weathersdk.WeatherSdk
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Internal use only.
 */
@Singleton
internal class ForecastDismissSignalProvider @Inject constructor() {
    fun get() = WeatherSdk.getInstance().forecastDismissSignal
}