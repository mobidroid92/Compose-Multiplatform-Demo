package com.myapplication.model.utils

import kotlin.coroutines.cancellation.CancellationException

object NetworkUtils {

    suspend fun <T> makeNetworkCall(call: suspend () -> T): OneShotOperationResult<T> {
        return try {
            OneShotOperationResult.Success(data = call())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            OneShotOperationResult.Error(msg = e.toString(), throwable = e)
        }
    }
}
