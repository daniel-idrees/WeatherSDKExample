package com.example.weathersdk.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
internal fun MainNavHost(cityName: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = FORECAST_ROUTE,
    ) {
        composable(
            route = FORECAST_ROUTE,
            arguments = listOf(navArgument(CITY_ARG) {
                type = NavType.StringType; defaultValue = cityName
            })
        ) { ForecastScreenView() }
    }
}

internal const val CITY_ARG = "city"
private const val FORECAST_ROUTE = "forecast/{$CITY_ARG}"
