package com.example.weathersdk

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SdkKeyManager @Inject constructor() {
    var key: String = ""
}