package com.example.weathersdk

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Internal use only.
 */
@Singleton
internal class WeatherSdkInstanceProvider @Inject constructor() {
    fun get() = WeatherSdk.getInstance()
}