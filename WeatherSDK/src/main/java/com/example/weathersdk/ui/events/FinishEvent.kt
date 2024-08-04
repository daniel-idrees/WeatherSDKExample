package com.example.weathersdk.ui.events

/**
 * Represents different types of finish events that can occur when forecast screen is dismissed
 */
sealed interface FinishEvent {
    /**
     * Represents a finish event occurring when the forecast screen is dismissed without any occurrence of error
     */
    data object OnFinished : FinishEvent

    /**
     * Represents a finish event occurring when the forecast screen is dismissed after an occurrence of error
     */
    data object OnFinishedWithError : FinishEvent
}