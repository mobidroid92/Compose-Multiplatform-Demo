package com.myapplication.pressentation.list

import com.myapplication.pressentation.uiModels.CharacterUiModel

data class CharactersListUiState(
    val isLoading: Boolean = false,
    val isFullRefresh: Boolean = false,
    val charactersList: List<CharacterUiModel> = emptyList(),
    val isShowError: Boolean = false
)