package com.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.myapplication.common.BuildConfig
import com.myapplication.model.local.database.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

actual fun createHttpClient() = HttpClient(CIO) {

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
            logger = Logger.ANDROID
            level = if(BuildConfig.DEBUG) {
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

        developmentMode = BuildConfig.DEBUG
    }

fun createAppDatabase(
    context: Context
): AppDatabase {
     return createDataBaseBuilder(context)
         .fallbackToDestructiveMigrationOnDowngrade(true)
         .setDriver(BundledSQLiteDriver())
         .setQueryCoroutineContext(Dispatchers.IO)
         .build()
}

private fun createDataBaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}