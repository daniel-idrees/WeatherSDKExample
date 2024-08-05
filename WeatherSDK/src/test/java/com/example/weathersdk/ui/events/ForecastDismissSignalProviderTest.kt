package com.example.weathersdk.ui.events

import com.example.weathersdk.WeatherSdk
import com.example.weathersdk.WeatherSdkInstanceProvider
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ForecastDismissSignalProviderTest {
    private val mockWeatherSdk: WeatherSdk = mock()

    private val mockWeatherSdkInstanceProvider: WeatherSdkInstanceProvider = mock {
        whenever(it.get()) doReturn mockWeatherSdk
    }

    private val subject by lazy { ForecastDismissSignalProvider(mockWeatherSdkInstanceProvider) }

    @Test
    fun `get should return forecastDismissSignal from WeatherSdk instance`() {
        val mockForecastDismissSignal: ForecastDismissSignal = mock()
        whenever(mockWeatherSdk.forecastDismissSignal).thenReturn(mockForecastDismissSignal)

        val result = subject.get()

        mockForecastDismissSignal shouldBe result
        verify(mockWeatherSdkInstanceProvider).get()
        verify(mockWeatherSdk).forecastDismissSignal
    }
}