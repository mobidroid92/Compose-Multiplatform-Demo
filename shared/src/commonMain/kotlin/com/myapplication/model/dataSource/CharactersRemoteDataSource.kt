package com.myapplication.model.dataSource

import com.myapplication.model.dto.CharacterDto
import com.myapplication.model.utils.OneShotOperationResult

fun interface CharactersRemoteDataSource {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

}