package com.myapplication.pressentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.myapplication.pressentation.list.CharactersListScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            CharactersListScreen()
        }
    }
}