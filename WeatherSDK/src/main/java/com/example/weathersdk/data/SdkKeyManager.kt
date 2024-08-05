package com.example.weathersdk.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Internal use only.
 *
 * Responsible for managing the SDK key.
 */
@Singleton
internal class SdkKeyManager @Inject constructor() {
    var key: String = ""
}