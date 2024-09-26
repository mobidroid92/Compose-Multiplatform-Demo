package com.myapplication.di.modules

import com.myapplication.di.createHttpClient
import com.myapplication.model.local.database.AppDatabase
import com.myapplication.model.network.dataSource.CharactersRemoteDataSource
import com.myapplication.model.network.dataSource.CharactersRemoteDataSourceImpl
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.repostries.CharactersRepositoryImpl
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<CharactersRemoteDataSource> {
        CharactersRemoteDataSourceImpl(createHttpClient())
    }
}

val localDataSourceModule = module {
    single {
        get<AppDatabase>().getCharactersDao()
    }
}

val reposModule = module {
    single<CharactersRepository> {
        CharactersRepositoryImpl(get(), get())
    }
}

val dataModule = module {
    includes(
        databaseModule, localDataSourceModule, remoteDataSourceModule, reposModule
    )
}