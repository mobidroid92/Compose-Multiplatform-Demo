package com.myapplication.model.utils

import io.ktor.client.plugins.*
import kotlin.coroutines.cancellation.CancellationException

object NetworkUtils {

    suspend fun <T> makeNetworkCall(call: suspend () -> T): OneShotOperationResult<T> {
        return try {
            val response: T = call()
            OneShotOperationResult.Success(data = response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: RedirectResponseException) { //3xx
            OneShotOperationResult.Error(msg = e.response.status.toString(), throwable = e)
        } catch (e: ClientRequestException) { //4xx
            OneShotOperationResult.Error(msg = e.response.status.toString(), throwable = e)
        } catch (e: ServerResponseException) { //5xx
            OneShotOperationResult.Error(msg = e.response.status.toString(), throwable = e)
        } catch (e: Exception) {
            OneShotOperationResult.Error(msg = e.toString(), throwable = e)
        }
    }

}
