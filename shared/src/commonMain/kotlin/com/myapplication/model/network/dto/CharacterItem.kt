package com.myapplication.model.network.dto

import com.myapplication.model.local.database.CharactersEntity
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

fun CharacterItem.toCharactersEntity(): CharactersEntity {
    return CharactersEntity(
        id = id,
        name = name,
        image = image
    )
}

fun List<CharacterItem>?.toCharacterEntityList(): List<CharactersEntity> {
    return this?.map { characterItem ->
        characterItem.toCharactersEntity()
    } ?: emptyList()
}