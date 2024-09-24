package com.myapplication.pressentation.characters.uiModels

import kotlinx.serialization.Serializable

@Serializable
data class CharacterUiModel(
    val id: Long,
    val name: String,
    val image: String
)