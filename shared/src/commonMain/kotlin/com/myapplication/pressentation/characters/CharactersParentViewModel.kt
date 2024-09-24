package com.myapplication.pressentation.characters

import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharactersParentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: Actions) {
        when (action) {
            is Actions.UpdateTopAppBarTitle -> updateTopAppBarTitle(action.title)
            is Actions.UpdateIsBackEnabled -> updateIsBackEnabled(action.previousBackStackEntry)
        }
    }

    private fun updateTopAppBarTitle(title: String) {
        _uiState.update {
            it.copy(topAppBarTitle = title)
        }
    }

    private fun updateIsBackEnabled(previousBackStackEntry: NavBackStackEntry?) {
        _uiState.update {
            it.copy(isBackEnabled = previousBackStackEntry != null)
        }
    }
}

data class UiState(
    val topAppBarTitle: String = "",
    val isBackEnabled: Boolean = false
)

sealed interface Actions {
    data class UpdateTopAppBarTitle(val title: String) : Actions
    data class UpdateIsBackEnabled(val previousBackStackEntry: NavBackStackEntry?) : Actions
}