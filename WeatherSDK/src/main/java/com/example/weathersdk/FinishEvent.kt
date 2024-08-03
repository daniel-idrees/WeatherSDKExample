package com.example.weathersdk

internal sealed interface FinishEvent {
    data object OnFinished : FinishEvent
    data object OnFinishedWithError : FinishEvent
}