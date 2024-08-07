package com.mygdx.game.WorldItems

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaEntity
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BookItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val animeliaType = Json.decodeFromJsonElement<BookItemCustomFields>(gameObjectData.customFields).AnimeliaType
    val animeliaBookText = getAnimeliaEntity(animeliaType)
    override val itemAquiredText = "You found the ${animeliaBookText} book!"
    override val texture = DefaultTextureHandler.getTexture("book.png")
}

@Serializable
data class BookItemCustomFields(val AnimeliaType: String){

}