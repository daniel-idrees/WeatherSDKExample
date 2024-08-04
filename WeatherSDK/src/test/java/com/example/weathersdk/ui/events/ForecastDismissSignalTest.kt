package com.example.weathersdk.ui.events

import app.cash.turbine.test
import com.example.weathersdk.data.common.MainDispatcherRule
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ForecastDismissSignalTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val subject by lazy { ForecastDismissSignal() }

    @Test
    fun `emitting an event should be collected by the events flow`() = runTest {
        subject.events.test {
            subject.emitEvent(FinishEvent.OnFinished)
            awaitItem() shouldBe FinishEvent.OnFinished

            subject.emitEvent(FinishEvent.OnFinishedWithError)
            awaitItem() shouldBe FinishEvent.OnFinishedWithError
        }
    }
}