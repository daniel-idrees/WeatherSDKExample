package com.example.weathersdk.ui.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ForecastDismissSignal @Inject constructor() {
    private val _events = MutableSharedFlow<FinishEvent>()
    val events: SharedFlow<FinishEvent>
        get() = _events

    internal suspend fun emitEvent(event: FinishEvent) {
        _events.emit(event)
    }
}