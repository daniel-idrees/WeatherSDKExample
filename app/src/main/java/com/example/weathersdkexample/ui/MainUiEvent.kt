package com.example.weathersdkexample.ui

sealed interface MainUiEvent {
    data object EmptyTextError : MainUiEvent
    data object ClearCityTextField: MainUiEvent
    data class UpdateCityTextField(val newText: String): MainUiEvent
}