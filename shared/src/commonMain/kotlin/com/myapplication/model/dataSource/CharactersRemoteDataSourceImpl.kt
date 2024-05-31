package com.myapplication.model.dataSource

import com.myapplication.model.utils.NetworkUtils
import com.myapplication.model.utils.OneShotOperationResult
import com.myapplication.model.dto.CharacterDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CharactersRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CharactersRemoteDataSource {

    override suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto> {
        return NetworkUtils.makeNetworkCall {
            httpClient.get("character/?$PARAM_PAGE=$pageNumber").body()
        }
    }

}