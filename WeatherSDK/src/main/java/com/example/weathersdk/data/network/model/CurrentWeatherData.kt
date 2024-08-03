package com.example.weathersdk.data.network.model

import com.squareup.moshi.Json

/**
 * Internal use only.
 */
internal data class CurrentWeatherData(
    @field:Json(name = "app_temp")
    val apparentTemperature: Double,
    @field:Json(name = "aqi")
    val airQualityIndex: Int?,
    @field:Json(name = "city_name")
    val cityName: String,
    @field:Json(name = "clouds")
    val cloudCoverage: Double?,
    @field:Json(name = "country_code")
    val countryCode: String,
    @field:Json(name = "dewpt")
    val dewPoint: Double?,
    @field:Json(name = "dhi")
    val diffuseHorizontalSolarIrradiance: Double?,
    @field:Json(name = "dni")
    val directNormalSolarIrradiance: Double?,
    @field:Json(name = "elev_angle")
    val solarElevationAngle: Double?,
    @field:Json(name = "ghi")
    val globalHorizontalSolarIrradiance: Double?,
    @field:Json(name = "gust")
    val windGustSpeed: Double?,
    @field:Json(name = "lat")
    val latitude: Double,
    @field:Json(name = "lon")
    val longitude: Double,
    @field:Json(name = "ob_time")
    val lastObservationTime: String,
    @field:Json(name = "pod")
    val partOfTheDay: String,
    @field:Json(name = "precip")
    val precipitation: Double?,
    @field:Json(name = "pres")
    val pressure: Double?,
    @field:Json(name = "rh")
    val relativeHumidity: Int?,
    @field:Json(name = "slp")
    val seaLevelPressure: Double?,
    @field:Json(name = "snow")
    val snowfall: Double?,
    @field:Json(name = "solar_rad")
    val estimatedSolarRadiation: Double?,
    val sources: List<String>,
    @field:Json(name = "state_code")
    val stateCode: String,
    @field:Json(name = "sunrise")
    val sunriseTime: String,
    @field:Json(name = "sunset")
    val sunsetTime: String,
    @field:Json(name = "temp")
    val temperature: Double,
    val timezone: String,
    @field:Json(name = "ts")
    val lastObservationTimeStamp: Long,
    @field:Json(name = "uv")
    val uvIndex: Int?,
    @field:Json(name = "vis")
    val visibility: Double?,
    val weather: Weather,
    @field:Json(name = "wind_cdir")
    val windDirectionShort: String?,
    @field:Json(name = "wind_cdir_full")
    val windDirectionFull: String?,
    @field:Json(name = "wind_dir")
    val windDirection: Double?,
    @field:Json(name = "wind_spd")
    val windSpeed: Double?
)