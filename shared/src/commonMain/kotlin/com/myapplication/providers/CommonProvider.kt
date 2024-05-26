package com.myapplication.providers

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.client.HttpClient
import org.koin.compose.currentKoinScope

const val BASE_CHARACTERS_HOST = "rickandmortyapi.com"
const val BASE_CHARACTERS_PATH = "api/"

expect fun getHttpClient(): HttpClient

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}