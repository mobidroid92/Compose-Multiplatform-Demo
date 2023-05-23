package com.myapplication.providers

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.PlatformUtils
import kotlinx.serialization.json.Json

actual fun getHttpClient() = HttpClient(Darwin) {

        expectSuccess = true

        //Serializer
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        //Logger
        install(Logging) {
            level = if(PlatformUtils.IS_DEVELOPMENT_MODE) {
                LogLevel.ALL
            } else {
                LogLevel.NONE
            }
        }

        //Defaults
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_CHARACTERS_HOST
                path(BASE_CHARACTERS_PATH)
            }
        }

        developmentMode = PlatformUtils.IS_DEVELOPMENT_MODE
    }