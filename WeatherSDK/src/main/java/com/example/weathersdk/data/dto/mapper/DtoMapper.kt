package com.example.weathersdk.data.dto.mapper

import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.Forecast
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.helper.DateTimeHelper
import com.example.weathersdk.data.dto.helper.DateTimeHelper.HHMM_PATTERN
import com.example.weathersdk.data.dto.helper.DateTimeHelper.YYYYMMDD_HHMM_PATTERN
import com.example.weathersdk.data.dto.helper.DateTimeHelper.YYYYMMDD_T_HHMM_PATTERN
import com.example.weathersdk.data.network.model.CurrentWeatherResult
import com.example.weathersdk.data.network.model.HourlyForcastData
import com.example.weathersdk.data.network.model.HourlyForecastResult

/**
 * Internal use only.
 *
 * Converts a [CurrentWeatherResult] to a [CurrentWeather] object.
 *
 * This function extracts the first item from the `data` list in the [CurrentWeatherResult]
 * and maps it to a [CurrentWeather] object.
 *
 * @return A [CurrentWeather] object containing the city name, temperature, weather description,
 *         and the formatted local time.
 */
internal fun CurrentWeatherResult.toCurrentWeather(): CurrentWeather {
    val currentWeatherData = data.first()

    return CurrentWeather(
        city = currentWeatherData.cityName,
        temperature = "${currentWeatherData.temperature}",
        description = currentWeatherData.weather.description,
        localTime = DateTimeHelper.formatDateTime(
            inputPattern = YYYYMMDD_HHMM_PATTERN,
            outputPattern = HHMM_PATTERN,
            timeInString = currentWeatherData.lastObservationTime,
            inputTimeZone = "GMT",
            outputTimezone = currentWeatherData.timezone
        )
    )
}

/**
 * Internal use only.
 *
 * Converts a [HourlyForecastResult] to a [HourlyForecast] object.
 *
 * @return An [HourlyForecast] object containing a list of [Forecast] objects.
 */
internal fun HourlyForecastResult.toHourlyForecast(): HourlyForecast {
    val list = arrayListOf<Forecast>()
    data.forEach { item ->
        list.add(item.toForecast())
    }
    return HourlyForecast(list)
}

/**
 * Internal use only.
 *
 * Converts an [HourlyForcastData] object to a [Forecast] object.
 *
 * @return A [Forecast] object containing the temperature, formatted time, and weather description.
 */
private fun HourlyForcastData.toForecast(): Forecast =
    Forecast(
        temperature = temperature.toString(),
        time = DateTimeHelper.formatDateTime(
            inputPattern = YYYYMMDD_T_HHMM_PATTERN,
            outputPattern = HHMM_PATTERN,
            timeInString = timeStampLocal,
        ),
        description = weather.description
    )
