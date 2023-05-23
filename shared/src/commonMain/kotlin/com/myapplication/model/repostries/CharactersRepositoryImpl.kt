package com.myapplication.model.repostries

import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.model.dto.CharacterDto
import com.myapplication.model.dataSource.CharactersRemoteDataSource

class CharactersRepositoryImpl(
    private val remote: CharactersRemoteDataSource,
) : CharactersRepository {

    override suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto> {
        return remote.getCharactersByPageNumber(pageNumber)
    }

}