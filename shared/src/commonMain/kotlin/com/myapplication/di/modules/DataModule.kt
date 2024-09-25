package com.myapplication.di.modules

import com.myapplication.di.createHttpClient
import com.myapplication.model.dataSource.CharactersRemoteDataSource
import com.myapplication.model.dataSource.CharactersRemoteDataSourceImpl
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.repostries.CharactersRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<CharactersRemoteDataSource> {
        CharactersRemoteDataSourceImpl(createHttpClient())
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(get())
    }
}