package com.example.weathersdk.di

import com.example.weathersdk.SdkKeyManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface SdkKeyEntryPoint {
    fun getSdkKeyManager(): SdkKeyManager
}