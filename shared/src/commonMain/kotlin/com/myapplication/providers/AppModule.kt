package com.myapplication.providers

import com.myapplication.model.dataSource.CharactersRemoteDataSource
import com.myapplication.model.dataSource.CharactersRemoteDataSourceImpl
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.repostries.CharactersRepositoryImpl
import org.koin.dsl.module

val appModule = module {

    single<CharactersRemoteDataSource> {
        CharactersRemoteDataSourceImpl(getHttpClient())
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(get())
    }
}