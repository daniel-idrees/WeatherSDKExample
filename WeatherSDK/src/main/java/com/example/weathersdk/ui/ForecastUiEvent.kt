package com.example.weathersdk.ui;

internal sealed interface ForecastUiEvent {
    data object Dismiss : ForecastUiEvent
}