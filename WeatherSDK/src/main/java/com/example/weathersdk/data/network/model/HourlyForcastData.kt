package com.example.weathersdk.data.network.model

import com.squareup.moshi.Json

/**
 * Internal use only.
 */
internal data class HourlyForcastData(
    @field:Json(name = "app_temp")
    val apparentTemperature: Double?,
    @field:Json(name = "clouds")
    val cloudCoverage: Double?,
    @field:Json(name = "clouds_hi")
    val cloudCoverageHighLevel: Double?,
    @field:Json(name = "clouds_low")
    val cloudCoverageLowLevel: Double?,
    @field:Json(name = "clouds_mid")
    val cloudCoverageMidLevel: Double?,
    @field:Json(name = "dewpt")
    val dewPoint: Double?,
    @field:Json(name = "dhi")
    val diffuseHorizontalSolarIrradiance: Double?,
    @field:Json(name = "dni")
    val directNormalSolarIrradiance: Double?,
    @field:Json(name = "ghi")
    val globalHorizontalSolarIrradiance: Double?,
    @field:Json(name = "ozone")
    val averageOzone: Int?,
    @field:Json(name = "pod")
    val partOfTheDay: String?,
    @field:Json(name = "pop")
    val precipitationProbability: Double?,
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
    @field:Json(name = "snow_depth")
    val snowDepth: Int?,
    @field:Json(name = "solar_rad")
    val estimatedSolarRadiation: Double?,
    @field:Json(name = "temp")
    val temperature: Double,
    @field:Json(name = "timestamp_local")
    val timeStampLocal: String,
    @field:Json(name = "timestamp_utc")
    val timeStampUtc: String,
    @field:Json(name = "ts")
    val timeStamp: Long,
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
    @field:Json(name = "wind_gust_spd")
    val windGustSpeed: Double?,
    @field:Json(name = "wind_spd")
    val windSpeed: Double?
)