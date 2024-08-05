package com.example.weathersdkexample.sdk

import com.example.weathersdk.WeatherSdk
import com.example.weathersdk.ui.events.FinishEvent
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


class WeatherSdkManager @Inject constructor(
    private val weatherSdk: WeatherSdk
) {
    fun displayWeatherForecast(cityName: String) = weatherSdk.displayWeatherForecast(cityName)
    fun getForecastDismissSignalEvents(): SharedFlow<FinishEvent> =
        weatherSdk.forecastDismissSignal.events
}