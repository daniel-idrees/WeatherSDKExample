# Weather SDK for Android

**Integration Guide**

The Weather SDK for Android provides the weather forecast features to be added in your apps.

This guide will walk you through integrating the Weather SDK into your Android app. Follow these
steps to successfully set up and use the SDK.

#### Prerequisites

Ensure that you have the following prerequisites before integrating the SDK:

- Minimum SDK version: 24 (Android 7.0)
- A valid API key from [Weatherbit.io](https://www.weatherbit.io/)

### Getting Started:

#### Add the library to ```build.gradle```

```
dependencies {
    implementation 'com.example:weather-sdk:1.0.0'
}
```

#### Creating the WeatherSdk instance

To create an instance of the ```WeatherSdk``` class, you need a valid api key that you can get from
[Weatherbit.io](https://www.weatherbit.io/). You can only create the sdk instance through the Builder function.

Example:

```
val weatherSdk: WeatherSdk =
    WeatherSdk.Builder()
    .setApiKey({Your API KEY})
    .build({context})
```

**Configuration**

##### Mandatory Configuration

1. You need to set the api key with the Builder instance. If you do not set it and try to use the build function from the Builder, an ```IllegalArgumentException``` is thrown.
2. You need to provide a ```Context``` in the build function of the Builder instance.   

**Usage**

##### Displaying the Weather Forecast

You can display the weather forecast of the given city with the instance of ```WeatherSdk```.

Example:

```
// Displaying the weather forecast for the given city
weatherSdk.displayWeatherForecast(cityName = "Munich")
```

#### Observing the Finish Events

You can observe the finish event that is triggered when the forecast screen is dismissed. Through
this event, you can know on your side if there was an error on the forecast screen.

Example:

```
// SharedFlow<FinishEvent>
weatherSdk.forecastDismissSignal.events.collect { event ->
    when (event) {
        FinishEvent.OnFinished -> // Do Something When SDK was dismissed and there was no error
        FinishEvent.OnFinishedWithError -> // Do Something When SDK was dismissed and there was error
    }
}
```
