package com.example.weathersdk.data.repository

import app.cash.turbine.test
import com.example.weathersdk.data.common.MainDispatcherRule
import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.Forecast
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import com.example.weathersdk.data.network.WeatherNetworkApi
import com.example.weathersdk.utils.FakeObjects.fakeCity
import com.example.weathersdk.utils.FakeObjects.fakeCurrentWeatherResponse
import com.example.weathersdk.utils.FakeObjects.fakeHourlyForecastResponse
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever


internal class WeatherNetworkRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val network: WeatherNetworkApi = mock()

    private val subject by lazy { WeatherNetworkRepository(network) }

    @Test
    fun `getCurrentWeather should return success when api response is successful and list is not empty`() =
        runTest {
            // when
            whenever(network.getCurrentWeather(fakeCity)) doReturn fakeCurrentWeatherResponse

            // then
            val result = subject.getCurrentWeather(fakeCity)
            verify(network).getCurrentWeather(fakeCity)
            verifyNoMoreInteractions(network)

            val expected = CurrentWeather(
                city = "Munich",
                temperature = "18.3",
                description = "Scattered clouds",
                localTime = "23:52",
            )

            result.test {
                awaitItem() shouldBe WeatherResult.Success(expected)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getCurrentWeather should return general error when api response throws general exception`() =
        runTest {
            // when
            whenever(network.getCurrentWeather(fakeCity)) doThrow IllegalArgumentException()

            // then
            val result = subject.getCurrentWeather(fakeCity)
            verify(network).getCurrentWeather(fakeCity)
            verifyNoMoreInteractions(network)

            result.test {
                awaitItem() shouldBe WeatherResult.Error
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getHourlyForecast should return success when api response is successful and list is not empty`() =
        runTest {
            // when
            whenever(network.getHourlyForecast(fakeCity)) doReturn fakeHourlyForecastResponse

            // then
            val result = subject.getHourlyForecast(fakeCity)
            verify(network).getHourlyForecast(fakeCity)
            verifyNoMoreInteractions(network)

            val expected = HourlyForecast(
                listOf(
                    Forecast(time = "02:00", temperature = "18.6", description = "Light rain"),
                    Forecast(time = "03:00", temperature = "18.0", description = "Light rain"),
                )
            )

            result.test {
                awaitItem() shouldBe WeatherResult.Success(expected)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getHourlyForecast should return general error when api response throws general exception`() =
        runTest {
            // when
            whenever(network.getHourlyForecast(fakeCity)) doThrow IllegalArgumentException()

            // then
            val result = subject.getHourlyForecast(fakeCity)
            verify(network).getHourlyForecast(fakeCity)
            verifyNoMoreInteractions(network)

            result.test {
                awaitItem() shouldBe WeatherResult.Error
                cancelAndIgnoreRemainingEvents()
            }
        }

}