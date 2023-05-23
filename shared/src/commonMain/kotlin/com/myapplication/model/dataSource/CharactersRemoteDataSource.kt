package com.myapplication.model.dataSource

import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.model.dto.CharacterDto

interface CharactersRemoteDataSource {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

}