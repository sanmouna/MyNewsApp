1. Project Title
# 📰 MyNewsApp - Kotlin MVVM
2. Description
   A simple News Feed Android application built using Kotlin, MVVM architecture, Room for offline storage, and Retrofit for API calls. The app supports category-based news, offline reading, WebView integration, and modern development practices.
3. Tech Stack
   Kotlin
MVVM Architecture
Retrofit2
Room DB
Coroutines + Flow
Dagger Hilt (Optional)
RecyclerView
WebView
JUnit & Mockito for testing
4. Features
   ✅ Category-based news (Tech, Sports, Business, etc.)

✅ Pull to refresh

✅ Offline article saving

✅ WebView for full articles

✅ Clean MVVM Architecture

✅ Room persistence

✅ Unit & UI testing

5. Setup Instructions
   git clone https://github.com/sanmouna/MyNewsApp.git
   cd news-app
   open with Android Studio
6. Dependencies
   gradle
   // Retrofit
   implementation "com.squareup.retrofit2:retrofit:2.9.0"
   implementation "com.squareup.retrofit2:converter-gson:2.9.0"

// Room
implementation "androidx.room:room-runtime:2.5.0"
kapt "androidx.room:room-compiler:2.5.0"

// Coroutine & Flow
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

// ViewModel & Lifecycle
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"

// Dagger Hilt (Optional)
implementation "com.google.dagger:hilt-android:2.46"
kapt "com.google.dagger:hilt-android-compiler:2.46"
7. Run Tests

# To run unit tests
./gradlew test


8. Author
    Created with Santhosh Jammikunta