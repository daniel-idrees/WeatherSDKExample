package com.example.weathersdkexample.di

import android.content.Context
import com.example.weathersdk.WeatherSdk
import com.example.weathersdkexample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {

    @Provides
    @Singleton
    fun provideWeatherSdk(@ApplicationContext context: Context): WeatherSdk =
        WeatherSdk.Builder()
            .setApiKey(BuildConfig.API_KEY)
            .build(context)
}