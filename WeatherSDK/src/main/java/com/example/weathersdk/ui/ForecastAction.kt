package com.example.weathersdk.ui

internal sealed interface ForecastAction {
    data object BackButtonPressed: ForecastAction
}