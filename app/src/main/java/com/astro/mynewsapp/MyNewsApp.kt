package com.astro.mynewsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyNewsApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}