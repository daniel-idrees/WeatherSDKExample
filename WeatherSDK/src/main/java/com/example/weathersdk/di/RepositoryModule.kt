package com.example.weathersdk.di

import com.example.weathersdk.data.repository.WeatherNetworkRepository
import com.example.weathersdk.data.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun providesWeatherRepository(
        impl: WeatherNetworkRepository,
    ): WeatherRepository
}