package com.myapplication.model.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(

    @SerialName("info") val info: Info,
    @SerialName("results") val results: List<CharacterItem>

)