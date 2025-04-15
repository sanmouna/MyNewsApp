plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    // Room
    implementation ("androidx.room:room-runtime:2.7.0")
    kapt ("androidx.room:room-compiler:2.7.0")
    implementation ("androidx.room:room-ktx:2.7.0")


    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    kapt ("com.github.bumptech.glide:compiler:4.16.0")

    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    implementation ("androidx.fragment:fragment-ktx:1.8.6")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation ("com.google.android.material:material:1.12.0")

    implementation ("androidx.viewpager2:viewpager2:1.1.0")


    implementation ("androidx.appcompat:appcompat:1.7.0")
    // Core Kotlin Extensions (KTX)
    implementation(libs.androidx.core.ktx)

    // Lifecycle Components (KTX)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Activity Compose Integration
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Jetpack Compose UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    // OkHttp Logging Interceptor
    implementation(libs.logging.interceptor)

    // Unit Testing
    testImplementation(libs.junit)

    // AndroidX JUnit for Android-specific unit tests
    androidTestImplementation(libs.androidx.junit)

    // Espresso UI Testing
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose UI Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
