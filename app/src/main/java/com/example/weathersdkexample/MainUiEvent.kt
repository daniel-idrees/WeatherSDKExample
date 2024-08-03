package com.example.weathersdkexample

sealed interface MainUiEvent {
    data object EmptyTextError : MainUiEvent
}