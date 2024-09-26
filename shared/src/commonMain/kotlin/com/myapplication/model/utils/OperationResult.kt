package com.myapplication.model.utils

interface OperationResult<T>

sealed class OneShotOperationResult<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
    val msg: String? = null
) : OperationResult<T> {

    class Success<T>(data: T? = null, msg: String? = null) :
        OneShotOperationResult<T>(data = data, msg = msg)

    class Error<T>(data: T? = null, msg: String? = null, throwable: Throwable? = null) :
        OneShotOperationResult<T>(data = data, throwable = throwable, msg = msg)
}