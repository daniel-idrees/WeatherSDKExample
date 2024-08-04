package com.example.weathersdkexample.ui

sealed interface MainAction {
    data class ForecastButtonClicked(val cityName: String): MainAction
}