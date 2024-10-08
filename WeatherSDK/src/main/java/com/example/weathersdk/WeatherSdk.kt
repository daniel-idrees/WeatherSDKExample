package com.example.weathersdk

import android.content.Context
import android.content.Intent
import com.example.weathersdk.di.SdkKeyEntryPoint
import com.example.weathersdk.ui.WeatherActivity
import com.example.weathersdk.ui.WeatherActivity.Companion.CITY_BUNDLE_KEY
import com.example.weathersdk.ui.events.ForecastDismissSignal
import dagger.hilt.EntryPoints
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * A class representing the Weather SDK, used to interact with weather-related functionalities.
 *
 * This class provides methods to configure the SDK and display weather forecasts.
 * It uses the builder pattern for construction and requires an API key for initialization.
 * Use WeatherSdk.Builder().build() to create the sdk instance
 *
 * @param context The application context used to start activity, access resources and services.
 * @param sdkKey The API key required for the SDK to fetch weather data.
 */

class WeatherSdk private constructor(
    @ApplicationContext private val context: Context,
    private val sdkKey: String,
    val forecastDismissSignal: ForecastDismissSignal
) {

    init {
        EntryPoints.get(context, SdkKeyEntryPoint::class.java)
            .getSdkKeyManager().apply {
                key = sdkKey
            }
    }

    /**
     * A builder class for constructing instance of [WeatherSdk].
     *
     * The builder allows setting the API key and provides a build method to create an instance of [WeatherSdk].
     */
    class Builder {
        private var sdkKey = ""

        /**
         * Sets the API key required for the SDK.
         *
         * @param apiKey The API key to be used for the SDK.
         * @return The [Builder] instance with the API key set.
         */
        fun setApiKey(apiKey: String) = apply { this.sdkKey = apiKey }


        /**
         * Constructs an sdk instance with the provided context and API key.
         *
         * @param context The application context used to access resources and services.
         * @return A new instance of sdk configured with the provided API key.
         * @throws IllegalArgumentException If the API key is not set or is empty.
         */
        fun build(context: Context): WeatherSdk {
            if (sdkKey.isEmpty()) {
                throw IllegalArgumentException("Api key is not set or is empty. Please set it with WeatherSdk.Builder().setApiKey({APIKEY})")
            }

            // // Synchronize to ensure thread-safe singleton initialization
            return synchronized(this) {
                WeatherSdk(context.applicationContext, sdkKey, ForecastDismissSignal()).apply {
                    sdkInstance = this
                }
            }
        }
    }

    /**
     * Starts an activity to display the weather forecast for the specified city.
     *
     * It launches the weather forecast screen with the provided city name.
     * The `city` parameter is used to fetch and display the weather information for that location.
     *
     * @param city The name of the city for which the weather forecast will be displayed.
     */
    fun displayWeatherForecast(city: String) {
        val intent = Intent(context, WeatherActivity::class.java).apply {
            putExtra(CITY_BUNDLE_KEY, city)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        }
        context.startActivity(intent)
    }

    internal companion object {
        @Volatile
        private var sdkInstance: WeatherSdk? = null

        /**
         *
         * Gets the singleton instance of [WeatherSdk].
         *
         * Used for SDK properties access internally.
         *
         * @throws IllegalStateException If [WeatherSdk] is not initialized.
         */
        fun getInstance(): WeatherSdk {
            return sdkInstance ?: throw IllegalStateException("WeatherSdk is not initialized")
        }
    }
}