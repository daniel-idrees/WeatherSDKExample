package com.example.weathersdkexample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathersdk.WeatherSdk
import com.example.weathersdk.ui.events.FinishEvent
import com.example.weathersdk.ui.events.ForecastDismissSignal
import com.example.weathersdkexample.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val sdk: WeatherSdk,
    private val forecastDismissSignal: ForecastDismissSignal,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val actions: MutableSharedFlow<MainAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _events = Channel<MainUiEvent>(capacity = 32)
    val events: Flow<MainUiEvent> = _events.receiveAsFlow()

    val forecastDismissSignalEvents: SharedFlow<FinishEvent> = forecastDismissSignal.events

    init {
        actions
            .onEach(::handleAction)
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    private fun showWeatherForecast(city: String) {
        sdk.displayWeatherForecast(appContext, city)
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
        }
    }
}