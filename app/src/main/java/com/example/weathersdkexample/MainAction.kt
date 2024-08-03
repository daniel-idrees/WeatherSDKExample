package com.example.weathersdkexample

sealed interface MainAction {
    data class ForecastButtonClicked(val cityName: String): MainAction
}