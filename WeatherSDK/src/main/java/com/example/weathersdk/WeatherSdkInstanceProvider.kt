package com.example.weathersdk

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WeatherSdkInstanceProvider @Inject constructor() {
    fun get() = WeatherSdk.getInstance()
}