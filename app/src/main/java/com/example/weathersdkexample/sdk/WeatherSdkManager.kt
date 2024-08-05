package com.example.weathersdkexample.sdk

import com.example.weathersdk.WeatherSdk
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdk.ui.events.ForecastDismissSignal
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


class WeatherSdkManager @Inject constructor(
    private val weatherSdk: WeatherSdk,
    private val forecastDismissSignal: ForecastDismissSignal
) {
    fun getForecastDismissSignalEvents(): SharedFlow<FinishEvent> = forecastDismissSignal.events
    fun displayWeatherForecast(cityName: String) = weatherSdk.displayWeatherForecast(cityName)
}