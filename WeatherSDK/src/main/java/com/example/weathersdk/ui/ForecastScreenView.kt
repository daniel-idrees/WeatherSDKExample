@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weathersdk.ui

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weathersdk.R
import com.example.weathersdk.data.dto.CurrentWeather
import com.example.weathersdk.data.dto.Forecast
import com.example.weathersdk.data.dto.HourlyForecast
import com.example.weathersdk.ui.theme.ContainerBackground
import com.example.weathersdk.ui.theme.ContainerTextColor
import com.example.weathersdk.ui.theme.DefaultTextColor
import com.example.weathersdk.ui.theme.DividerColor
import com.example.weathersdk.ui.views.LoadingView

/**
 * Internal use only.
 */
@Composable
internal fun ForecastScreenView(
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                ForecastUiEvent.Dismiss -> {
                    context.getActivity()?.finish()
                }
            }
        }
    }

    LaunchedEffect(viewModel.finishEvent) {
        viewModel.finishEvent.collect { event ->
            when (event) {
                FinishEvent.OnFinished -> {
                    Log.d(
                        "WeatherSdk",
                        "user has tapped on a back button and the SDK's fragment can be dismissed"
                    )
                }

                FinishEvent.OnFinishedWithError -> {
                    Log.d("WeatherSdk", "an error occurred and the SDK's fragment can be dismissed")
                }

                else -> {
                    /* do nothing */
                }
            }
        }
    }

    val forecastViewState by viewModel.hourlyForecastViewState.collectAsStateWithLifecycle()
    val currentWeatherViewState by viewModel.currentWeatherViewState.collectAsStateWithLifecycle()

    MainView(
        currentWeatherViewState,
        forecastViewState,
        viewModel::onAction
    )
}

@Composable
private fun MainView(
    currentWeatherViewState: CurrentWeatherViewState,
    forecastViewState: HourlyForecastViewState,
    onAction: (ForecastAction) -> Unit,
) {
    val onBackPressAction = { onAction(ForecastAction.BackButtonPressed) }

    BackHandler {
        onBackPressAction()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.forecast_top_bar_text),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = ContainerTextColor
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onBackPressAction()
                        },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "back button",
                        tint = ContainerTextColor
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = ContainerBackground)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            WeatherDetailView(modifier = Modifier.padding(top = 10.dp), currentWeatherViewState)
            HourlyForecastListView(modifier = Modifier.padding(top = 5.dp), forecastViewState)
        }
    }
}

@Composable
private fun WeatherDetailView(modifier: Modifier, viewState: CurrentWeatherViewState) {

    when (viewState) {
        CurrentWeatherViewState.Error -> {
            Box(modifier = Modifier.height(66.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    text = "Current Weather is not available at the moment.",
                    color = DefaultTextColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        CurrentWeatherViewState.Loading -> LoadingView(
            modifier = Modifier
                .height(66.dp)
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        is CurrentWeatherViewState.Success -> Column(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = stringResource(
                    id = R.string.weather_in_city_text,
                    viewState.currentWeather.city
                ),
                color = DefaultTextColor,
                fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    id = R.string.current_temperature_text,
                    viewState.currentWeather.temperature
                ),
                color = DefaultTextColor,
                fontSize = 22.sp
            )
            Text(
                text = viewState.currentWeather.description,
                color = DefaultTextColor,
                fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    id = R.string.at_local_time_text,
                    viewState.currentWeather.localTime
                ).uppercase(),
                color = DefaultTextColor,
                fontSize = 12.sp
            )
        }
    }

}

@Composable
private fun HourlyForecastListView(modifier: Modifier, forecastViewState: HourlyForecastViewState) {
    when (forecastViewState) {
        HourlyForecastViewState.Error -> {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .background(color = ContainerBackground),
                text = "Forecast not available at the moment.",
                color = ContainerTextColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        HourlyForecastViewState.Loading -> LoadingView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        )

        is HourlyForecastViewState.Success -> {
            LazyColumn(
                modifier = modifier.background(color = ContainerBackground),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(forecastViewState.hourlyForecast.forecasts) { forecast ->
                    HourlyForecastView(forecast)

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = DividerColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun HourlyForecastView(forecast: Forecast) {
    Row(
        modifier = Modifier
            .padding(11.dp)
            .fillMaxWidth()
            .background(color = ContainerBackground),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forecast.time,
            color = ContainerTextColor,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(
                id = R.string.current_temperature_text,
                forecast.temperature
            ),
            fontWeight = FontWeight.Bold,
            color = ContainerTextColor,
            fontSize = 16.sp
        )
        Text(
            text = forecast.description,
            color = ContainerTextColor,
            fontSize = 16.sp
        )
    }
}

private fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenPreview() {
    MainView(
        CurrentWeatherViewState.Success(previewCurrentWeather),
        HourlyForecastViewState.Success(previewHourlyForecast)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenHourlyForecastErrorPreview() {
    MainView(
        CurrentWeatherViewState.Success(previewCurrentWeather),
        HourlyForecastViewState.Error
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenCurrentWeatherErrorPreview() {
    MainView(
        CurrentWeatherViewState.Error,
        HourlyForecastViewState.Success(previewHourlyForecast)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenLoaderOnForecastPreview() {
    MainView(
        CurrentWeatherViewState.Success(previewCurrentWeather),
        HourlyForecastViewState.Loading
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenLoaderOnBothPreview() {
    MainView(
        CurrentWeatherViewState.Loading,
        HourlyForecastViewState.Loading
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenLoaderOnCurrentWeatherPreview() {
    MainView(
        CurrentWeatherViewState.Loading,
        HourlyForecastViewState.Success(previewHourlyForecast)
    ) {}
}

private val previewCurrentWeather = CurrentWeather("Munich", "29.7", "Few Clouds", "15:15")

private val previewHourlyForecast = HourlyForecast(
    forecasts = listOf(
        Forecast("16:00", "29.3", "Clear Sky"),
        Forecast("17:00", "28.3", "Few Clouds"),
        Forecast("18:00", "27.3", "Raining"),
        Forecast("19:00", "26.3", "Overcast"),
        Forecast("20:00", "25.3", "Clear Sky"),
        Forecast("21:00", "24.3", "Clear Sky"),
    )
)