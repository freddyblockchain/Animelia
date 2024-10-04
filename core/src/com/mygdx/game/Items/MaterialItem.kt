package com.mygdx.game.Items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.generalSaveState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


enum class Material{ANIMELIABONE}

fun getMaterialTexture(material: Material): Texture{
    return when(material){
        Material.ANIMELIABONE -> DefaultTextureHandler.getTexture("AnimeliaBone.png")
    }
}

class MaterialItem(gameObjectData: GameObjectData, var material: Material? = null) : WorldItem(gameObjectData) {

    init {
        // Make it so you can both init materials from the editor, but also ingame
        if(material == null){
            val itemText = Json.decodeFromJsonElement<MaterialItemCustomFields>(gameObjectData.customFields).Item
            material = Material.values().find { it.name == itemText }
        }
    }

    override val itemAquiredText = "You found a ${material!!.name}!"
    override val texture = getMaterialTexture(material!!)

    override fun itemGained() {
        val currentValue =  generalSaveState.inventory.materialItems[material]!!
        generalSaveState.inventory.materialItems[material!!] = currentValue + 1
        generalSaveState.updateSaveState()
        this.remove()
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
    }
}

@Serializable
data class MaterialItemCustomFields(val Item: String){

}