package com.myapplication

import android.app.Application
import com.myapplication.di.initKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(applicationContext)
    }
}