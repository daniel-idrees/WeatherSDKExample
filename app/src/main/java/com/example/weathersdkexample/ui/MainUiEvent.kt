package com.example.weathersdkexample.ui

sealed interface MainUiEvent {
    data object EmptyTextError : MainUiEvent
}