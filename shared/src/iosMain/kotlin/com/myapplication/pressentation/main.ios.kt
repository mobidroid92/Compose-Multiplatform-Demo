package com.myapplication.pressentation

import androidx.compose.ui.window.ComposeUIViewController
import com.myapplication.providers.appModule
import com.myapplication.providers.viewModelModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}

private fun initKoin() {
    startKoin {
        modules(appModule, viewModelModule)
    }
}