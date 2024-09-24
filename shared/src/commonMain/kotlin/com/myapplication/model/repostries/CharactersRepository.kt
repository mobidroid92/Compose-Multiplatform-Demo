package com.myapplication.model.repostries

import com.myapplication.model.dto.CharacterDto
import com.myapplication.model.utils.OneShotOperationResult

fun interface CharactersRepository {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

}