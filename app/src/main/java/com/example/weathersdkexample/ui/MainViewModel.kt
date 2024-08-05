package com.example.weathersdkexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdkexample.di.DefaultDispatcher
import com.example.weathersdkexample.sdk.WeatherSdkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val weatherSdkManager: WeatherSdkManager,
) : ViewModel() {

    private val actions: MutableSharedFlow<MainAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _events = Channel<MainUiEvent>(capacity = 32)
    val events: Flow<MainUiEvent> = _events.receiveAsFlow()

    val forecastDismissSignalEvents: SharedFlow<FinishEvent> =
        weatherSdkManager.getForecastDismissSignalEvents()

    init {
        actions
            .onEach(::handleAction)
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    private fun showWeatherForecast(city: String) {
        weatherSdkManager.displayWeatherForecast(city)
    }

    fun onAction(action: MainAction) = actions.tryEmit(action)

    private suspend fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.ForecastButtonClicked -> {
                if (action.cityName.isNotBlank()) {
                    showWeatherForecast(action.cityName)
                } else {
                    _events.send(MainUiEvent.EmptyTextError)
                }
            }
            is MainAction.CityTextFieldTextChange -> _events.send(MainUiEvent.UpdateCityTextField(action.newText))
            MainAction.ClearButtonClicked -> _events.send(MainUiEvent.ClearCityTextField)
        }
    }
}