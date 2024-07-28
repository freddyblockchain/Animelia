package com.mygdx.game.Ability

import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class AbilityItem(gameObjectData: GameObjectData) : GameObject(gameObjectData) {
    override val texture = DefaultTextureHandler.getTexture("Box.png")
    override val layer = Layer.ONGROUND

    val abilityCustomFields = Json.decodeFromJsonElement<AbilityCustomFields>(gameObjectData.customFields).Ability
}

@Serializable
data class AbilityCustomFields(val Ability: String){

}
