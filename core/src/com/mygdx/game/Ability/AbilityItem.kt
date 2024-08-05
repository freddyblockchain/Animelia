package com.mygdx.game.Ability

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val layer = Layer.ONGROUND

    val abilityString = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
    val abilityType = convertAbilityToType(abilityString)
    val abilityData = convertAbilityTypeToData(abilityType)
    override val texture = DefaultTextureHandler.getTexture(abilityData.imageIcon)

    init {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}

@Serializable
data class AbilityCustomFields(val Ability: String){

}
