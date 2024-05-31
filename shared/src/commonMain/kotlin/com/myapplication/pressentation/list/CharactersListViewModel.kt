package com.myapplication.pressentation.list

import androidx.lifecycle.ViewModel
import com.myapplication.model.dto.toCharacterUiModelList
import com.myapplication.model.dataSource.CharactersPaginator
import com.myapplication.model.repostries.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersListViewModel(
    charactersRepository: CharactersRepository,
    private val scope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersListUiState())
    val state = _state.asStateFlow()

    private val charactersPaginator = CharactersPaginator(
        charactersRepository = charactersRepository,
        onLoadUpdated = { isLoading, isFullRefresh ->
            _state.update {
                it.copy(isLoading = isLoading, isFullRefresh = isFullRefresh)
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
                    isShowError = false
                )
            }
        },
        onError = {
            _state.update {
                it.copy(isShowError = true)
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
        scope.launch(ioDispatcher) {
            charactersPaginator.loadNextItems(isFullRefresh)
        }
    }

}

sealed interface Actions {
    data object ReloadCharacters : Actions
    data object LoadNextCharactersPage : Actions
}