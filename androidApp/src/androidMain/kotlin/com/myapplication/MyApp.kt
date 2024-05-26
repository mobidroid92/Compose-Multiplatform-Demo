package com.myapplication

import android.app.Application
import com.myapplication.providers.initKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(applicationContext)
    }
}