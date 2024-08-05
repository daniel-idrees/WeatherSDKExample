package com.example.weathersdk.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

/**
 * Internal use only.
 *
 * Injection of Coroutine Dispatchers.
 * Reference: https://www.valueof.io/blog/injecting-coroutines-dispatchers-with-dagger
 */
@Module
@InstallIn(SingletonComponent::class)
internal class CoroutineDispatchersModule {

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @ApplicationScope
    @Provides
    fun providesApplicationScope(
        @MainDispatcher
        mainDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class ApplicationScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoDispatcher