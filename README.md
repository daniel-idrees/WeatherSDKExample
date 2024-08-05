# WeatherSDKExample
**Gini Tech Challenge**

An example Android app which uses an Android SDK that allows Android apps to add a weather forecast feature.

#### SDK Data Source

The SDK uses [Weatherbit API](https://www.weatherbit.io/) to retreive weather information.

#### API key 

Ensure that your project's ```local.properties``` file contains the Weatherbit API key, like this:

```API_KEY="your_api_key_here"```

#### Technologies and Tools 

- Kotlin 
- Model-View-ViewModel (MVVM) architectural pattern
- Hilt for dependency injection
- Coroutines for asynchronous operations
- Flows for handling data asynchronously
- Jetpack Compose for building UI
- Junit/Mockito/Kotest for unit testing

#### Documentation

There are two types of documentation: reference documentation and integration guide.

The reference documentation is part of the source code (kdoc or javadoc) and is compiled to a static website using Dokka for both java and kotlin source.

##### How to build the documentation

The reference documentation can be built using gradle with the following command:

```$ ./gradlew dokkaHtml```

The build documentation can be found under ```WeatherSDKExample/WeatherSDK/build/dokka/```   

The following commands open the reference documentation in the browser:

```$ open WeatherSDKExample/WeatherSDK/build/dokka/html/index.html```


#### Demo

https://github.com/user-attachments/assets/c99d09de-2393-4d90-b697-6574abc1d3d1

#### Limitations 

- Usage on Tablet or wider devices is not tested.
- Theme configuration can be improved.
- Dark mode is not handled
- No explicit handling of error during internet unavailability.
