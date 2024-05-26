package com.myapplication.providers

import com.myapplication.pressentation.list.CharactersListViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val viewModelModule = module {
    single {
        CharactersListViewModel(
            get(),
            get(named(APPLICATION_SCOPE)),
            get(named(IO_DISPATCHER))
        )
    }
}