package com.myapplication.pressentation

import androidx.compose.ui.window.ComposeUIViewController
import com.myapplication.di.modules.dataModule
import com.myapplication.di.modules.viewModelModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}

private fun initKoin() {
    startKoin {
        modules(dataModule, viewModelModule)
    }
}