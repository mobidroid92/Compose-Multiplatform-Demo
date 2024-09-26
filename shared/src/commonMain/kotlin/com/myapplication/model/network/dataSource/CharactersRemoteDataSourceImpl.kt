package com.myapplication.model.network.dataSource

import com.myapplication.model.network.dto.CharacterDto
import com.myapplication.model.utils.NetworkUtils
import com.myapplication.model.utils.OneShotOperationResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharactersRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CharactersRemoteDataSource {

    override suspend fun getCharactersByPageNumber(pageNumber: Int): OneShotOperationResult<CharacterDto> {
        return NetworkUtils.makeNetworkCall {
            httpClient.get("$CHARACTERS_PATH/?$PARAM_PAGE=$pageNumber").body()
        }
    }

    companion object {
        const val CHARACTERS_PATH = "character"
    }
}