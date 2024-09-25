package com.myapplication.di

import io.ktor.client.HttpClient

const val BASE_CHARACTERS_HOST = "rickandmortyapi.com"
const val BASE_CHARACTERS_PATH = "api/"

expect fun createHttpClient(): HttpClient