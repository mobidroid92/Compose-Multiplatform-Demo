package com.myapplication.model.dto

import com.myapplication.pressentation.characters.uiModels.CharacterUiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterItem(

    @SerialName("created") val created: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("gender") val gender: String,
    @SerialName("id") val id: Long,
    @SerialName("image") val image: String,
    @SerialName("location") val location: Location,
    @SerialName("name") val name: String,
    @SerialName("origin") val origin: Origin,
    @SerialName("species") val species: String,
    @SerialName("status") val status: String,
    @SerialName("type") val type: String,
    @SerialName("url") val url: String

)

fun CharacterItem.toCharacterUiModel(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        image = image
    )
}

fun List<CharacterItem>?.toCharacterUiModelList(): List<CharacterUiModel> {
    return this?.map { characterItem ->
        characterItem.toCharacterUiModel()
    } ?: emptyList()
}