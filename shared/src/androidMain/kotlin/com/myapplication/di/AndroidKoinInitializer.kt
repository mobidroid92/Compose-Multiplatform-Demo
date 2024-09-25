package com.myapplication.di

import android.content.Context
import com.myapplication.di.modules.dataModule
import com.myapplication.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun initKoin(context: Context) {
    startKoin {
        androidContext(context)
        androidLogger()
        modules(dataModule, viewModelModule)
    }
}