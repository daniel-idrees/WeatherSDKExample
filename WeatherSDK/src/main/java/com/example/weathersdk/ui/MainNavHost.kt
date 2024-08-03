package com.example.weathersdk.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * Internal use only.
 *
 * Main navigation host for the weather forecast application.
 *
 * This function sets up the navigation graph with a single forecast route.
 *
 * @param cityName The name of the city to display the weather forecast for.
 */
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

/**
 * Argument key for passing the city name in the navigation route.
 */
internal const val CITY_ARG = "city"

/**
 * Navigation graph route for the forecast screen with city name as argument.
 */
private const val FORECAST_ROUTE = "forecast/{$CITY_ARG}"
