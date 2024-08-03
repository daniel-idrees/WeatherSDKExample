@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.weathersdk.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersdk.FinishEvent
import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.data.dto.WeatherResult
import com.example.weathersdk.data.repository.WeatherRepository
import com.example.weathersdk.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        val city = checkNotNull(savedStateHandle.get<String>(CITY_ARG))
        getWeather(city)
    }

    private val actions: MutableSharedFlow<ForecastAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _uiEvents = Channel<ForecastUiEvent>(capacity = 32)
    val uiEvents: Flow<ForecastUiEvent> = _uiEvents.receiveAsFlow()

    private val _finishEvent = MutableStateFlow<FinishEvent?>(null)
    val finishEvent: StateFlow<FinishEvent?>
        get() = _finishEvent

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
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            supervisorScope {
                launch {
                    weatherRepository.getCurrentWeather(city)
                        .catch {
                            _currentWeatherViewState.update { CurrentWeatherViewState.Error }
                        }.distinctUntilChanged()
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
                launch {
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
        }
    }

    fun onAction(action: ForecastAction) = actions.tryEmit(action)

    private suspend fun handleAction(action: ForecastAction) {
        when (action) {
            ForecastAction.BackButtonPressed -> {
                if (currentWeatherViewState.value is CurrentWeatherViewState.Success && hourlyForecastViewState.value is HourlyForecastViewState.Success) {
                    _finishEvent.tryEmit(FinishEvent.OnFinished)
                } else {
                    _finishEvent.tryEmit(FinishEvent.OnFinishedWithError)
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
