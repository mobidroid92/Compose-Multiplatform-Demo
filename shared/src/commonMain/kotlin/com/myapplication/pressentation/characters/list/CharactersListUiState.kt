package com.myapplication.pressentation.characters.list

import com.myapplication.pressentation.characters.uiModels.CharacterUiModel

data class CharactersListUiState(
    val isLoading: Boolean = false,
    val isFullRefresh: Boolean = false,
    val isShouldShowPullToRefreshIndicator: Boolean = false,
    val isShouldShowLoadMoreItem: Boolean = false,
    val isShouldShowLoadingShimmer: Boolean = false,
    val charactersList: List<CharacterUiModel> = emptyList(),
    val isShowErrorItem: Boolean = false
)