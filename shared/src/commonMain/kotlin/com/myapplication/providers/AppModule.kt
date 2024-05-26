package com.myapplication.providers

import com.myapplication.model.dataSource.CharactersRemoteDataSource
import com.myapplication.model.dataSource.CharactersRemoteDataSourceImpl
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.repostries.CharactersRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val APPLICATION_SCOPE = "application_scope"
const val IO_DISPATCHER = "io_dispatcher"

val appModule = module {

    single<CharactersRemoteDataSource> {
        CharactersRemoteDataSourceImpl(getHttpClient())
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(get())
    }

    single(named(APPLICATION_SCOPE)) {
        CoroutineScope(SupervisorJob())
    }

    single(named(IO_DISPATCHER)) {
        Dispatchers.IO
    }

}