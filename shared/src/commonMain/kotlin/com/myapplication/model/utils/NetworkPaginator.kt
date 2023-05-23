package com.myapplication.model.utils

open class NetworkPaginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean, Boolean) -> Unit,
    private val onRequest: suspend (Key) -> OneShotOperationResult<Item>,
    private val getNextKey: suspend (Item) -> Key?,
    private val onSuccess: suspend (Item, Boolean) -> Unit,
    private val onError: suspend (OneShotOperationResult.Error<Item>?) -> Unit,
    private val onReachEnd: (suspend () -> Unit)? = null
) {

    private var currentKey: Key? = initialKey
    private var isMakingRequest = false

    suspend fun loadNextItems(isFullRefresh: Boolean) {
        if (isFullRefresh) {
            currentKey = initialKey
        }
        if (isMakingRequest || currentKey == null) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true, isFullRefresh)
        val result = onRequest(currentKey!!)
        isMakingRequest = false
        onLoadUpdated(false, isFullRefresh)
        when (result) {
            is OneShotOperationResult.Error -> {
                onError(result)
            }

            is OneShotOperationResult.Success -> {
                currentKey = getNextKey(result.data!!)
                onSuccess(result.data, isFullRefresh)
                if (currentKey == null) {
                    onReachEnd?.invoke()
                }
            }
        }
    }

}