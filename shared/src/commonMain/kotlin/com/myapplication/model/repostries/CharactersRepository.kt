package com.myapplication.model.repostries

import com.myapplication.model.network.dto.CharacterDto
import com.myapplication.model.network.dto.CharacterItem
import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.pressentation.characters.uiModels.CharacterUiModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

    suspend fun insertCharactersToDb(characters: List<CharacterItem>)

    fun getCharactersFlowFromDb(): Flow<List<CharacterUiModel>>

    suspend fun getCharacterByIdFromDb(id: Long): CharacterUiModel

    suspend fun clearCharactersDb()
}