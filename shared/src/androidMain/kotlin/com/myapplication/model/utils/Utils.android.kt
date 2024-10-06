package com.myapplication.model.utils

import com.myapplication.common.BuildConfig

actual val isDebug: Boolean
    get() = BuildConfig.DEBUG