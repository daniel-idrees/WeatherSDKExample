package com.example.weathersdk.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.weathersdk.data.common.MainDispatcherRule
import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.Forecast
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import com.example.weathersdk.data.repository.WeatherRepository
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdk.ui.events.ForecastDismissSignal
import com.example.weathersdk.utils.FakeObjects.fakeCity
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ForecastViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val savedStateHandle: SavedStateHandle = mock {
        whenever(mock.get<String>(CITY_ARG)) doReturn fakeCity
    }

    private val weatherRepository: WeatherRepository = mock()

    private val forecastDismissSignal: ForecastDismissSignal = mock ()

    private val subject by lazy {
        ForecastViewModel(
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            weatherRepository = weatherRepository,
            savedStateHandle = savedStateHandle,
            forecastDismissSignal = forecastDismissSignal
        )
    }

    @Test
    fun `currentWeatherViewState should be success if the repository returns current weather`() =
        runTest {

            //when
            whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    CurrentWeather(
                        city = "Munich",
                        temperature = "18.3",
                        description = "Scattered clouds",
                        localTime = "23:52",
                    )
                )
            )

            //then
            subject.currentWeatherViewState.test {
                val result = awaitItem()
                result shouldBe CurrentWeatherViewState.Success(
                    CurrentWeather(
                        city = "Munich",
                        temperature = "18.3",
                        description = "Scattered clouds",
                        localTime = "23:52",
                    )
                )

                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `currentWeatherViewState should be error if the repository returns error`() = runTest {
        // when
        whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(WeatherResult.Error)

        // then
        subject.currentWeatherViewState.test {
            val result = awaitItem()
            result shouldBe CurrentWeatherViewState.Error
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `hourlyForecastViewState should be success if the repository returns hourly forecast`() =
        runTest {
            //when
            whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    HourlyForecast(
                        listOf(
                            Forecast(
                                time = "02:00",
                                temperature = "18.6",
                                description = "Light rain"
                            ),
                            Forecast(
                                time = "03:00",
                                temperature = "18.0",
                                description = "Light rain"
                            ),
                        )
                    )
                )
            )

            //then
            subject.hourlyForecastViewState.test {
                val result = awaitItem()
                result shouldBe HourlyForecastViewState.Success(
                    HourlyForecast(
                        listOf(
                            Forecast(
                                time = "02:00",
                                temperature = "18.6",
                                description = "Light rain"
                            ),
                            Forecast(
                                time = "03:00",
                                temperature = "18.0",
                                description = "Light rain"
                            ),
                        )
                    )
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `hourlyForecastViewState should be error if the repository returns error`() = runTest {
        // when
        whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(WeatherResult.Error)

        // then
        subject.hourlyForecastViewState.test {
            val result = awaitItem()
            result shouldBe HourlyForecastViewState.Error
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `BackButtonPressed action should send the ui event to dismiss the screen`() = runTest {
        subject.uiEvents.test {
            subject.onAction(ForecastAction.BackButtonPressed)
            awaitItem() shouldBe ForecastUiEvent.Dismiss
        }
    }

    @Test
    fun `BackButtonPressed action should trigger the OnFinished event when viewstates were successful`() =
        runTest {
            //when
            whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    CurrentWeather(
                        city = "Munich",
                        temperature = "18.3",
                        description = "Scattered clouds",
                        localTime = "23:52",
                    )
                )
            )

            whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    HourlyForecast(
                        listOf(
                            Forecast(
                                time = "02:00",
                                temperature = "18.6",
                                description = "Light rain"
                            ),
                            Forecast(
                                time = "03:00",
                                temperature = "18.0",
                                description = "Light rain"
                            ),
                        )
                    )
                )
            )
            subject.onAction(ForecastAction.BackButtonPressed)

            //then
            verify(forecastDismissSignal).emitEvent(FinishEvent.OnFinished)
        }

    @Test
    fun `BackButtonPressed action should trigger the OnFinishedWithError event when viewstates have error`() =
        runTest {
            //when
            whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(WeatherResult.Error)
            whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(WeatherResult.Error)
            subject.onAction(ForecastAction.BackButtonPressed)

            //then
            verify(forecastDismissSignal).emitEvent(FinishEvent.OnFinishedWithError)
        }

    @Test
    fun `BackButtonPressed action should trigger the OnFinishedWithError event when hourly view state has error`() =
        runTest {
            //when
            whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    CurrentWeather(
                        city = "Munich",
                        temperature = "18.3",
                        description = "Scattered clouds",
                        localTime = "23:52",
                    )
                )
            )
            whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(WeatherResult.Error)
            subject.onAction(ForecastAction.BackButtonPressed)

            //then
            verify(forecastDismissSignal).emitEvent(FinishEvent.OnFinishedWithError)
        }

    @Test
    fun `BackButtonPressed action should trigger the OnFinishedWithError event when current weather view state has error`() =
        runTest {
            //when
            whenever(weatherRepository.getCurrentWeather(fakeCity)) doReturn flowOf(WeatherResult.Error)
            whenever(weatherRepository.getHourlyForecast(fakeCity)) doReturn flowOf(
                WeatherResult.Success(
                    HourlyForecast(
                        listOf(
                            Forecast(
                                time = "02:00",
                                temperature = "18.6",
                                description = "Light rain"
                            ),
                            Forecast(
                                time = "03:00",
                                temperature = "18.0",
                                description = "Light rain"
                            ),
                        )
                    )
                )
            )
            whenever(forecastDismissSignal.emitEvent(FinishEvent.OnFinished)).thenReturn(Unit)
            whenever(forecastDismissSignal.emitEvent(FinishEvent.OnFinishedWithError)).thenReturn(Unit)

            subject.onAction(ForecastAction.BackButtonPressed)

            //then
            verify(forecastDismissSignal).emitEvent(FinishEvent.OnFinishedWithError)
        }
}