package com.example.weathersdk.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Internal use only.
 */
internal data class CurrentWeatherData(
    @SerializedName("app_temp")
    val apparentTemperature: Double?,
    @SerializedName("aqi")
    val airQualityIndex: Int?,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("clouds")
    val cloudCoverage: Double?,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("dewpt")
    val dewPoint: Double?,
    @SerializedName("dhi")
    val diffuseHorizontalSolarIrradiance: Double?,
    @SerializedName("dni")
    val directNormalSolarIrradiance: Double?,
    @SerializedName("elev_angle")
    val solarElevationAngle: Double?,
    @SerializedName("ghi")
    val globalHorizontalSolarIrradiance: Double?,
    @SerializedName("gust")
    val windGustSpeed: Double?,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("ob_time")
    val lastObservationTime: String,
    @SerializedName("pod")
    val partOfTheDay: String,
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
    @SerializedName("solar_rad")
    val estimatedSolarRadiation: Double?,
    val sources: List<String>,
    @SerializedName("state_code")
    val stateCode: String,
    @SerializedName("sunrise")
    val sunriseTime: String,
    @SerializedName("sunset")
    val sunsetTime: String,
    @SerializedName("temp")
    val temperature: Double,
    val timezone: String,
    @SerializedName("ts")
    val lastObservationTimeStamp: Long,
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
    @SerializedName("wind_spd")
    val windSpeed: Double?
)