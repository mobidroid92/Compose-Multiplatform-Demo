package com.myapplication.model.repostries

import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.model.dto.CharacterDto

interface CharactersRepository {

    suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto>

}