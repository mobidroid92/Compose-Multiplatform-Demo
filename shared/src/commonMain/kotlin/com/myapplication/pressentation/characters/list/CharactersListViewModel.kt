package com.myapplication.pressentation.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.model.network.dataSource.CharactersPaginator
import com.myapplication.model.repostries.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersListViewModel(
    private val charactersRepository: CharactersRepository
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
                    isShouldShowLoadingShimmer = isLoading && isFullRefresh && it.charactersList.isEmpty(),
                    isShowErrorItem = false
                )
            }
        },
        onSuccess = { characterDto, isFullRefresh ->
            if (isFullRefresh) {
                charactersRepository.clearCharactersDb()
            }
            charactersRepository.insertCharactersToDb(characterDto.results)

            _state.update {
                it.copy(
                    isFullRefresh = isFullRefresh,
                    isShouldShowPullToRefreshIndicator = false,
                    isShouldShowLoadMoreItem = false,
                    isShouldShowLoadingShimmer = false,
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
                    isShouldShowLoadingShimmer = false,
                    isShowErrorItem = it.charactersList.isNotEmpty()
                )
            }
        }
    )

    init {
        loadCharactersFromDb()
        handleActions(Actions.ReloadCharacters)
    }

    fun handleActions(action: Actions) {
        when (action) {
            Actions.ReloadCharacters -> getCharacters(isFullRefresh = true)
            Actions.LoadNextCharactersPage -> getCharacters(isFullRefresh = false)
        }
    }

    private fun loadCharactersFromDb() {
        viewModelScope.launch {
            charactersRepository.getCharactersFlowFromDb().collect { list ->
                _state.update {
                    it.copy(
                        charactersList = list
                    )
                }
            }
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