package com.example.weathersdk.utils

import com.example.weathersdk.data.network.model.CurrentWeatherData
import com.example.weathersdk.data.network.model.CurrentWeatherResult
import com.example.weathersdk.data.network.model.HourlyForcastData
import com.example.weathersdk.data.network.model.HourlyForecastResult
import com.example.weathersdk.data.network.model.Weather

internal object FakeObjects {
    const val fakeCity = "Munich"
    val fakeCurrentWeatherResponse = CurrentWeatherResult(
        count = 1,
        data = listOf(
            CurrentWeatherData(
                apparentTemperature = 18.4,
                airQualityIndex = 56,
                cityName = "Munich",
                cloudCoverage = 49.0,
                countryCode = "DE",
                dewPoint = 14.9,
                diffuseHorizontalSolarIrradiance = 0.0,
                directNormalSolarIrradiance = 0.0,
                solarElevationAngle = -22.27,
                globalHorizontalSolarIrradiance = 0.0,
                windGustSpeed = 4.6,
                latitude = 48.13743,
                longitude = 11.57549,
                lastObservationTime = "2024-08-03 21:52",

                partOfTheDay = "n",
                precipitation = 0.0,
                pressure = 954.0,
                relativeHumidity = 81,
                seaLevelPressure = 1014.0,
                snowfall = 0.0,
                estimatedSolarRadiation = 0.0,
                sources = listOf("analysis", "D1024", "radar", "satellite"),
                stateCode = "02",
                sunriseTime = "18:44",
                sunsetTime = "18:44",
                temperature = 18.3,
                timezone = "Europe/Berlin",
                lastObservationTimeStamp = 1722721975,
                uvIndex = 0,
                visibility = 16.0,
                weather = Weather(code = 802, description = "Scattered clouds", icon = "c02n"),
                windDirectionShort = "SW",
                windDirectionFull = "southwest",
                windDirection = 232.0,
                windSpeed = 2.6
            )
        )
    )

    val fakeHourlyForecastResponse = HourlyForecastResult(
        cityName = "Munich",
        countryCode = "DE",
        data = listOf(
            HourlyForcastData(
                apparentTemperature = 18.7,
                cloudCoverage = 98.0,
                cloudCoverageHighLevel = 100.0,
                cloudCoverageLowLevel = 48.0,
                cloudCoverageMidLevel = 100.0,
                dewPoint = 15.8,
                diffuseHorizontalSolarIrradiance = 0.0,
                directNormalSolarIrradiance = 0.0,
                globalHorizontalSolarIrradiance = 0.0,
                averageOzone = 326,
                partOfTheDay = "n",
                precipitationProbability = 45.0,
                precipitation = 0.8,
                pressure = 954.0,
                relativeHumidity = 84,
                seaLevelPressure = 1015.0,
                snowfall = 0.0,
                snowDepth = 0,
                estimatedSolarRadiation = 0.0,
                temperature = 18.6,
                timeStampLocal = "2024-08-04T02:00:00",
                timeStampUtc = "2024-08-04T00:00:00",
                timeStamp = 1722729600,
                uvIndex = 0,
                visibility = 22.9,
                weather = Weather(
                    code = 500,
                    description = "Light rain",
                    icon = "r01n"
                ),
                windDirectionShort = "WSW",
                windDirectionFull = "west-southwest",
                windDirection = 240.0,
                windGustSpeed = 5.59,
                windSpeed = 2.57
            ),
            HourlyForcastData(
                apparentTemperature = 18.0,
                cloudCoverage = 99.0,
                cloudCoverageHighLevel = 100.0,
                cloudCoverageLowLevel = 28.0,
                cloudCoverageMidLevel = 65.0,
                dewPoint = 15.6,
                diffuseHorizontalSolarIrradiance = 0.0,
                directNormalSolarIrradiance = 0.0,
                globalHorizontalSolarIrradiance = 0.0,
                averageOzone = 327,
                partOfTheDay = "n",
                precipitationProbability = 35.0,
                precipitation = 0.5,
                pressure = 953.0,
                relativeHumidity = 86,
                seaLevelPressure = 1015.0,
                snowfall = 0.0,
                snowDepth = 0,
                estimatedSolarRadiation = 0.0,
                temperature = 18.0,
                timeStampLocal = "2024-08-04T03:00:00",
                timeStampUtc = "2024-08-04T01:00:00",
                timeStamp = 1722733200,
                uvIndex = 0,
                visibility = 16.4,
                weather = Weather(
                    code = 500,
                    description = "Light rain",
                    icon = "r01n"
                ),
                windDirectionShort = "WSW",
                windDirectionFull = "west-southwest",
                windDirection = 240.0,
                windGustSpeed = 6.0,
                windSpeed = 2.57
            ),
        ),
        longitude = "11.57549",
        latitude = "48.13743",
        stateCode = "02",
        timezone = "Europe/Berlin"
    )


}