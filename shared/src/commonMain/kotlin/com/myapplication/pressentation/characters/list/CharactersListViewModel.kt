package com.myapplication.pressentation.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.model.dataSource.CharactersPaginator
import com.myapplication.model.dto.toCharacterUiModelList
import com.myapplication.model.repostries.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersListViewModel(
    charactersRepository: CharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersListUiState())
    val state = _state.asStateFlow()

    private val charactersPaginator = CharactersPaginator(
        charactersRepository = charactersRepository,
        onLoadUpdated = { isLoading, isFullRefresh ->
            _state.update {
                it.copy(
                    isLoading = isLoading,
                    isFullRefresh = isFullRefresh,
                    isShouldShowPullToRefreshIndicator = isLoading && isFullRefresh,
                    isShouldShowLoadMoreItem = isLoading && isFullRefresh.not(),
                    isShouldShowEmptyPlaceholder = isLoading && isFullRefresh && it.charactersList.isEmpty(),
                    isShowErrorItem = false
                )
            }
        },
        onSuccess = { characterDto, isFullRefresh ->
            _state.update {
                it.copy(
                    charactersList = if (isFullRefresh) {
                        characterDto.results.toCharacterUiModelList()
                    } else {
                        it.charactersList.plus(
                            characterDto.results.toCharacterUiModelList()
                        )
                    },
                    isFullRefresh = isFullRefresh,
                    isShouldShowPullToRefreshIndicator = false,
                    isShouldShowLoadMoreItem = false,
                    isShouldShowEmptyPlaceholder = false,
                    isShowErrorItem = false
                )
            }
        },
        onError = {
            _state.update {
                it.copy(
                    isLoading = false,
                    isFullRefresh = false,
                    isShouldShowPullToRefreshIndicator = false,
                    isShouldShowLoadMoreItem = false,
                    isShouldShowEmptyPlaceholder = false,
                    isShowErrorItem = it.charactersList.isNotEmpty()
                )
            }
        }
    )

    init {
        handleActions(Actions.ReloadCharacters)
    }

    fun handleActions(action: Actions) {
        when (action) {
            Actions.ReloadCharacters -> getCharacters(isFullRefresh = true)
            Actions.LoadNextCharactersPage -> getCharacters(isFullRefresh = false)
        }
    }

    private fun getCharacters(isFullRefresh: Boolean) {
        viewModelScope.launch {
            charactersPaginator.loadNextItems(isFullRefresh)
        }
    }

}

sealed interface Actions {
    data object ReloadCharacters : Actions
    data object LoadNextCharactersPage : Actions
}