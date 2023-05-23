package com.myapplication.providers

import com.myapplication.model.dataSource.CharactersRemoteDataSource
import com.myapplication.model.dataSource.CharactersRemoteDataSourceImpl
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.repostries.CharactersRepositoryImpl
import com.myapplication.pressentation.list.CharactersListViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob

const val BASE_CHARACTERS_HOST = "rickandmortyapi.com"
const val BASE_CHARACTERS_PATH = "api/"

expect fun getHttpClient(): HttpClient

private val charactersRemoteDataSource: CharactersRemoteDataSource by lazy {
    CharactersRemoteDataSourceImpl(getHttpClient())
}

private val charactersRepository: CharactersRepository by lazy {
    CharactersRepositoryImpl(charactersRemoteDataSource)
}

private val applicationScope by lazy {
    CoroutineScope(SupervisorJob())
}

private val ioDispatcher by lazy {
    Dispatchers.IO
}

private val charactersListVM by lazy {
    CharactersListViewModel(
        charactersRepository,
        applicationScope,
        ioDispatcher
    )
}

fun getCharactersListViewModel() = charactersListVM