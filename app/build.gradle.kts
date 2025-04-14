plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.astro.mynewsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.astro.mynewsapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    dependencies {
        // Core Kotlin Extensions (KTX) for Android
        implementation(libs.androidx.core.ktx)

        // Lifecycle Components (KTX)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Jetpack Activity Compose Integration
        implementation(libs.androidx.activity.compose)

        // Jetpack Compose BOM
        implementation(platform(libs.androidx.compose.bom))

        // Jetpack Compose UI
        implementation(libs.androidx.ui)

        // Jetpack Compose Graphics
        implementation(libs.androidx.ui.graphics)

        // Jetpack Compose Tooling
        implementation(libs.androidx.ui.tooling.preview)

        // Jetpack Material 3
        implementation(libs.androidx.material3)

        // Retrofit
        implementation (libs.retrofit)
        implementation (libs.converter.gson)

        // OkHttp for logging (optional, useful for debugging)
        implementation (libs.logging.interceptor)

        // Gson for converting JSON
        implementation (libs.gson)


        // JUnit for Unit Testing
        testImplementation(libs.junit)

        // AndroidX JUnit for Android-specific unit tests
        androidTestImplementation(libs.androidx.junit)

        // Espresso for UI Testing in Android
        androidTestImplementation(libs.androidx.espresso.core)

        // Jetpack Compose BOM for Android Tests
        androidTestImplementation(platform(libs.androidx.compose.bom))

        // Jetpack Compose UI Testing in Android
        androidTestImplementation(libs.androidx.ui.test.junit4)

        // Debug-specific implementations for Compose UI
        debugImplementation(libs.androidx.ui.tooling)

        // Debug-specific UI test manifest (for testing environment setup)
        debugImplementation(libs.androidx.ui.test.manifest)
    }

}