package com.example.weathersdk.ui.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Responsible for broadcasting [FinishEvent] at the time of dismissal of the forecast screen.
 *
 * This class uses a [MutableSharedFlow] to emit events that signify different dismissal scenarios. The events
 * can be observed by components interested in handling these dismissal signals.
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
     * Emits a [FinishEvent] to the [events] flow. This method is used to signal different dismissal
     * events.
     *
     * @param event The [FinishEvent] to be emitted.
     */
    internal suspend fun emitEvent(event: FinishEvent) {
        _events.emit(event)
    }
}