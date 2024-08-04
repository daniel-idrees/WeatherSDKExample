package com.example.weathersdk.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Internal use only.
 */
internal data class HourlyForcastData(
    @SerializedName("app_temp")
    val apparentTemperature: Double?,
    @SerializedName("clouds")
    val cloudCoverage: Double?,
    @SerializedName("clouds_hi")
    val cloudCoverageHighLevel: Double?,
    @SerializedName("clouds_low")
    val cloudCoverageLowLevel: Double?,
    @SerializedName("clouds_mid")
    val cloudCoverageMidLevel: Double?,
    @SerializedName("dewpt")
    val dewPoint: Double?,
    @SerializedName("dhi")
    val diffuseHorizontalSolarIrradiance: Double?,
    @SerializedName("dni")
    val directNormalSolarIrradiance: Double?,
    @SerializedName("ghi")
    val globalHorizontalSolarIrradiance: Double?,
    @SerializedName("ozone")
    val averageOzone: Int?,
    @SerializedName("pod")
    val partOfTheDay: String?,
    @SerializedName("pop")
    val precipitationProbability: Double?,
    @SerializedName("precip")
    val precipitation: Double?,
    @SerializedName("pres")
    val pressure: Double?,
    @SerializedName("rh")
    val relativeHumidity: Int?,
    @SerializedName("slp")
    val seaLevelPressure: Double?,
    @SerializedName("snow")
    val snowfall: Double?,
    @SerializedName("snow_depth")
    val snowDepth: Int?,
    @SerializedName("solar_rad")
    val estimatedSolarRadiation: Double?,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("timestamp_local")
    val timeStampLocal: String,
    @SerializedName("timestamp_utc")
    val timeStampUtc: String,
    @SerializedName("ts")
    val timeStamp: Long,
    @SerializedName("uv")
    val uvIndex: Int?,
    @SerializedName("vis")
    val visibility: Double?,
    val weather: Weather,
    @SerializedName("wind_cdir")
    val windDirectionShort: String?,
    @SerializedName("wind_cdir_full")
    val windDirectionFull: String?,
    @SerializedName("wind_dir")
    val windDirection: Double?,
    @SerializedName("wind_gust_spd")
    val windGustSpeed: Double?,
    @SerializedName("wind_spd")
    val windSpeed: Double?
)