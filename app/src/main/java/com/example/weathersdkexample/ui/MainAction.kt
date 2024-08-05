package com.example.weathersdkexample.ui

sealed interface MainAction {
    data class ForecastButtonClicked(val cityName: String) : MainAction
    data object ClearButtonClicked : MainAction
    data class CityTextFieldTextChange(val newText: String) : MainAction
}