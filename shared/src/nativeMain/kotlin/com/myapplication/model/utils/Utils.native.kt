package com.myapplication.model.utils

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual val isDebug: Boolean
    get() = Platform.isDebugBinary