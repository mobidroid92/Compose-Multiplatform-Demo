package com.myapplication.model.repostries

import com.myapplication.model.local.database.CharactersDao
import com.myapplication.model.local.database.toCharacterUiModel
import com.myapplication.model.local.database.toCharacterUiModelList
import com.myapplication.model.network.dataSource.CharactersRemoteDataSource
import com.myapplication.model.network.dto.CharacterDto
import com.myapplication.model.network.dto.CharacterItem
import com.myapplication.model.network.dto.toCharacterEntityList
import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.pressentation.characters.uiModels.CharacterUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val remote: CharactersRemoteDataSource,
    private val dao: CharactersDao,
) : CharactersRepository {

    override suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto> {
        return remote.getCharactersByPageNumber(pageNumber)
    }

    override suspend fun insertCharactersToDb(characters: List<CharacterItem>) {
        dao.insertCharacters(characters.toCharacterEntityList())
    }

    override fun getCharactersFlowFromDb(): Flow<List<CharacterUiModel>> {
        return dao.getCharactersFlow().map { it.toCharacterUiModelList() } }

    override suspend fun getCharacterByIdFromDb(id: Long): CharacterUiModel {
        return dao.getCharacterById(id).toCharacterUiModel()
    }

    override suspend fun clearCharactersDb() {
        dao.clearCharacters()
    }
}