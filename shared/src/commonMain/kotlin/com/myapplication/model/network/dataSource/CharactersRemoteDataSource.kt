package com.myapplication.model.network.dataSource

import com.myapplication.model.network.dto.CharacterDto
import com.myapplication.model.utils.OneShotOperationResult

fun interface CharactersRemoteDataSource {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

}