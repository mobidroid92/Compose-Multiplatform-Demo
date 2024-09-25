package com.myapplication.di.modules

import com.myapplication.pressentation.characters.CharactersParentViewModel
import com.myapplication.pressentation.characters.list.CharactersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel {
        CharactersListViewModel(
            get()
        )
    }

    viewModel {
        CharactersParentViewModel()
    }
}