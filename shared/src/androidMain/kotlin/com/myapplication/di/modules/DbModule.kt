package com.myapplication.di.modules

import com.myapplication.di.createAppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


actual val databaseModule = module {
    single {
        createAppDatabase(androidContext())
    }
}