package com.myapplication.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(

    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("pages") val pages: Int,
    @SerialName("prev") val prev: String?

)