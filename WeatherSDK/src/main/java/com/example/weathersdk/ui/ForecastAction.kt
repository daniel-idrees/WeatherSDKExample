package com.example.weathersdk.ui

/**
 * Internal use only.
 *
 * Represents actions related to the forecast screen.
 */
internal sealed interface ForecastAction {
    /**
     * Action triggered when the back button is pressed on the forecast screen.
     */
    data object BackButtonPressed: ForecastAction
}