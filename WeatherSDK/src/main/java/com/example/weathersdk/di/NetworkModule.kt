package com.example.weathersdk.di

import com.example.weathersdk.SdkKeyManager
import com.example.weathersdk.data.network.WeatherNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(sdkKeyManager: SdkKeyManager): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                val original = chain.request()
                val urlWithQueryParam = original.url().newBuilder()
                    .addQueryParameter("key", sdkKeyManager.key)
                    .build()
                chain.proceed(original.newBuilder().url(urlWithQueryParam).build())
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)

    @Provides
    @Singleton
    fun provideWeatherNetworkApi(
        retrofitBuilder: Retrofit.Builder,
    ): WeatherNetworkApi =
        retrofitBuilder
            .baseUrl("https://api.weatherbit.io/")
            .build()
            .create(WeatherNetworkApi::class.java)
}