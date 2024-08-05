@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.weathersdk.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import com.example.weathersdk.data.repository.WeatherRepository
import com.example.weathersdk.di.DefaultDispatcher
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdk.ui.events.ForecastDismissSignalProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Internal use only.
 */
@HiltViewModel
internal class ForecastViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository,
    private val forecastDismissSignalProvider: ForecastDismissSignalProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val actions: MutableSharedFlow<ForecastAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _uiEvents = Channel<ForecastUiEvent>(capacity = 32)
    val uiEvents: Flow<ForecastUiEvent> = _uiEvents.receiveAsFlow()

    val forecastDismissSignalEvents: SharedFlow<FinishEvent> =
        forecastDismissSignalProvider.get().events

    private val _currentWeatherViewState: MutableStateFlow<CurrentWeatherViewState> =
        MutableStateFlow(CurrentWeatherViewState.Loading)

    val currentWeatherViewState: StateFlow<CurrentWeatherViewState>
        get() = _currentWeatherViewState

    private val _hourlyForecastViewState: MutableStateFlow<HourlyForecastViewState> =
        MutableStateFlow(HourlyForecastViewState.Loading)

    val hourlyForecastViewState: StateFlow<HourlyForecastViewState>
        get() = _hourlyForecastViewState

    init {
        actions
            .onEach(::handleAction)
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)

        val city = checkNotNull(savedStateHandle.get<String>(CITY_ARG))
        getWeather(city)
    }

    private fun getWeather(city: String) {

        viewModelScope.launch {
            weatherRepository.getCurrentWeather(city)
                .catch {
                    _currentWeatherViewState.update { CurrentWeatherViewState.Error }
                }
                .distinctUntilChanged()
                .mapLatest { result ->
                    when (result) {
                        WeatherResult.Error -> CurrentWeatherViewState.Error
                        is WeatherResult.Success -> CurrentWeatherViewState.Success(result.data)
                        WeatherResult.NoInternetConnection -> CurrentWeatherViewState.Error
                    }
                }
                .collect { uiState ->
                    _currentWeatherViewState.update { uiState }
                }
        }
        viewModelScope.launch {
            weatherRepository.getHourlyForecast(city)
                .catch {
                    _hourlyForecastViewState.update { HourlyForecastViewState.Error }
                }
                .distinctUntilChanged()
                .mapLatest { result ->
                    when (result) {
                        WeatherResult.Error -> HourlyForecastViewState.Error
                        is WeatherResult.Success -> HourlyForecastViewState.Success(result.data)
                        WeatherResult.NoInternetConnection -> HourlyForecastViewState.Error
                    }
                }
                .collect { uiState ->
                    _hourlyForecastViewState.update { uiState }
                }
        }
    }

    /**
     * Emits a [ForecastAction] to be handled.
     *
     * @param action The action to be emitted.
     */
    fun onAction(action: ForecastAction) = actions.tryEmit(action)

    /**
     * Handles the given [ForecastAction].
     *
     * @param action The action to be handled.
     */
    private suspend fun handleAction(action: ForecastAction) {
        when (action) {
            // trigger the finish and dismiss events
            ForecastAction.BackButtonPressed -> {
                // if both view states were successful, trigger OnFinished event otherwise OnFinishedWithError
                if (currentWeatherViewState.value is CurrentWeatherViewState.Success &&
                    hourlyForecastViewState.value is HourlyForecastViewState.Success
                ) {
                    forecastDismissSignalProvider.get().emitEvent(FinishEvent.OnFinished)
                } else {
                    forecastDismissSignalProvider.get().emitEvent(FinishEvent.OnFinishedWithError)
                }
                _uiEvents.send(ForecastUiEvent.Dismiss)
            }
        }
    }
}

internal sealed interface CurrentWeatherViewState {
    data class Success(val currentWeather: CurrentWeather) : CurrentWeatherViewState
    data object Error : CurrentWeatherViewState
    data object Loading : CurrentWeatherViewState
}

internal sealed interface HourlyForecastViewState {
    data class Success(val hourlyForecast: HourlyForecast) : HourlyForecastViewState
    data object Error : HourlyForecastViewState
    data object Loading : HourlyForecastViewState
}
