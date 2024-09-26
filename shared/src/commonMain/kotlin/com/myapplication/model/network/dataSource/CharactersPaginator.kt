package com.myapplication.model.network.dataSource

import com.eygraber.uri.Uri
import com.myapplication.model.network.dto.CharacterDto
import com.myapplication.model.repostries.CharactersRepository
import com.myapplication.model.utils.NetworkPaginator
import com.myapplication.model.utils.OneShotOperationResult

class CharactersPaginator(
    charactersRepository: CharactersRepository,
    onLoadUpdated: (Boolean, Boolean) -> Unit,
    onSuccess: suspend (CharacterDto, Boolean) -> Unit,
    onError: suspend (OneShotOperationResult.Error<CharacterDto>?) -> Unit,
    onReachEnd: (suspend () -> Unit)? = null
) : NetworkPaginator<Int, CharacterDto>(
    initialKey = 0,
    getNextKey = { characterDto ->
        var pageString: String? = null
        characterDto.info.next?.let { nextUrlNotNull ->
            pageString = Uri.parse(nextUrlNotNull).getQueryParameter(PARAM_PAGE)
        }
        pageString?.toInt()
    },
    onRequest = { pageNumber ->
        charactersRepository.getCharactersByPageNumber(pageNumber)
    },
    onLoadUpdated = onLoadUpdated,
    onSuccess = onSuccess,
    onError = onError,
    onReachEnd = onReachEnd
)

const val PARAM_PAGE = "page"