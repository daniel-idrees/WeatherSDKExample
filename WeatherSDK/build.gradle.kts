plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.google.dagger)
    alias(libs.plugins.jetbrains.dokka)
}

android {
    namespace = "com.example.weathersdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    buildToolsVersion = "34.0.0"
}

/*tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
    dokkaSourceSets {
        named("main") {
            includes.from("README.md")
            sourceRoots.from(file("src/main/kotlin"))
        }
    }
}*/

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.hilt.compiler)
    kapt(libs.hilt.compiler)
    debugImplementation(libs.ui.tooling)

    // Retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)

    //testing
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}