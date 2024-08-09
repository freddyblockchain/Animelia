package com.mygdx.game.WorldItems

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Ability.convertAbilityToType
import com.mygdx.game.Ability.convertAbilityTypeToData
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val abilityString = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
    val abilityType = convertAbilityToType(abilityString)
    val abilityData = convertAbilityTypeToData(abilityType)
    override val texture = DefaultTextureHandler.getTexture(abilityData.imageIcon)
    override val itemAquiredText = "You found the ${this.abilityType.name} Ability"

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}