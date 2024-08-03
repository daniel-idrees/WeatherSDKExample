package com.example.weathersdk.ui

/**
 *
 * Internal use only.
 *
 * Represents different types of finish events that can occur when forecast screen is dismissed
 */
internal sealed interface FinishEvent {
    /**
     * Represents a finish event occurring when the forecast screen is dismissed without any occurrence of error
     */
    data object OnFinished : FinishEvent

    /**
     * Represents a finish event occurring when the forecast screen is dismissed after an occurrence of error
     */
    data object OnFinishedWithError : FinishEvent
}