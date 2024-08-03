package com.example.weathersdk.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weathersdk.ui.theme.WeatherSDKTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = checkNotNull(intent.getStringExtra(CITY_BUNDLE_KEY))

        setContent {
            WeatherSDKTheme {
                MainNavHost(cityName = city)
            }
        }
    }

    companion object {
        const val CITY_BUNDLE_KEY = "CITY"
    }
}