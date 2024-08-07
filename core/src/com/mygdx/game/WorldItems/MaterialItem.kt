package com.mygdx.game.WorldItems

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class MaterialItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {
    val itemText = Json.decodeFromJsonElement<MaterialItemCustomFields>(gameObjectData.customFields).Item
    override val itemAquiredText = "You found a ${itemText}!"
    override val texture = DefaultTextureHandler.getTexture("IceFlower.png")
}

@Serializable
data class MaterialItemCustomFields(val Item: String){

}