package com.mygdx.game.WorldItems

import com.mygdx.game.Animelia.getAnimeliaEntity
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.generalSaveState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class BookItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {

    val animeliaType = Json.decodeFromJsonElement<BookItemCustomFields>(gameObjectData.customFields).AnimeliaType
    val animeliaBookText = getAnimeliaEntity(animeliaType)
    override val itemAquiredText = "You found the ${animeliaBookText} book!"
    override val texture = DefaultTextureHandler.getTexture("book.png")

    override fun itemGained() {
        super.itemGained()
        generalSaveState.inventory.entityBooks.add(getAnimeliaEntity(animeliaType))
        generalSaveState.updateSaveState()
    }
}

@Serializable
data class BookItemCustomFields(val AnimeliaType: String){

}