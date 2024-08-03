package com.example.weathersdk.ui;

/**
 *
 * Internal use only.
 *
 * Represents UI events related to the forecast screen.
 */
internal sealed interface ForecastUiEvent {
    /**
     * Event triggered when the forecast screen should be dismissed.
     */
    data object Dismiss : ForecastUiEvent
}