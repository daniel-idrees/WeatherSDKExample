package com.example.weathersdk.ui.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Responsible for managing and broadcasting [FinishEvent] at the time of dismissal of the forecast screen.
 *
 * This class provides a way to observe and respond to [FinishEvent]s.
 */
class ForecastDismissSignal internal constructor() {
    private val _events = MutableSharedFlow<FinishEvent>()

    /**
     * A [SharedFlow] that emits [FinishEvent] related to the dismissal of the forecast screen.
     *
     * Observers can collect from this flow to receive notifications about different [FinishEvent]s.
     */
    val events: SharedFlow<FinishEvent>
        get() = _events

    /**
     * Internal use only.
     *
     * Emits a [FinishEvent] to the [events] flow. This method is used to trigger the [FinishEvent].
     *
     * @param event The [FinishEvent] to be emitted. This should represent the specific type of finish event that occurred,
     * such as dismissal with success or with an error.
     */
    internal suspend fun emitEvent(event: FinishEvent) {
        _events.emit(event)
    }
}