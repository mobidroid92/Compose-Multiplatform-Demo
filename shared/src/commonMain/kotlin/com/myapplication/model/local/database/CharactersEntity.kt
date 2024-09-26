package com.myapplication.model.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapplication.pressentation.characters.uiModels.CharacterUiModel

@Entity
data class CharactersEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val image: String
)

fun CharactersEntity.toCharacterUiModel(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        image = image
    )
}

fun List<CharactersEntity>?.toCharacterUiModelList(): List<CharacterUiModel> {
    return this?.map { characterEntity ->
        characterEntity.toCharacterUiModel()
    } ?: emptyList()
}