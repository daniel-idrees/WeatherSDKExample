package com.example.weathersdk.data.dto.mapper

import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.Forecast
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.helper.DateTimeHelper
import com.example.weathersdk.data.dto.helper.DateTimeHelper.HHMM_PATTERN
import com.example.weathersdk.data.dto.helper.DateTimeHelper.YYYYMMDD_T_HHMM_PATTERN
import com.example.weathersdk.data.dto.helper.DateTimeHelper.YYYYMMDD_HHMM_PATTERN
import com.example.weathersdk.data.network.model.CurrentWeatherResult
import com.example.weathersdk.data.network.model.HourlyForcastData
import com.example.weathersdk.data.network.model.HourlyForecastResult

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

internal fun HourlyForecastResult.toHourlyForecast(): HourlyForecast {
    val list = arrayListOf<Forecast>()
    data.forEach { item ->
        list.add(item.toForecast())
    }
    return HourlyForecast(list)
}

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
