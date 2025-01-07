package com.mygdx.game.Items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Animelia.anivolutionCheck
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.GameObjectData
import com.mygdx.game.GameObjects.GameObject.GameObject
import com.mygdx.game.Utils.RandomManager
import com.mygdx.game.generalSaveState
import com.mygdx.game.player
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


enum class Material{ANIMELIABONE, ICEFRUIT, FIREFRUIT,CANYONFRUIT,FORESTFRUIT,TROPICALFRUIT}
enum class KeyItem{FROZENHEART,FIREHEART, MAP, CROWN}

fun getMaterialTexture(material: Material): Texture{
    return when(material){
        Material.ANIMELIABONE -> DefaultTextureHandler.getTexture("AnimeliaBone.png")
        Material.ICEFRUIT -> DefaultTextureHandler.getTexture("icefruit.png")
        Material.FORESTFRUIT -> DefaultTextureHandler.getTexture("forestfruit.png")
        Material.FIREFRUIT -> DefaultTextureHandler.getTexture("firefruit.png")
        Material.CANYONFRUIT -> DefaultTextureHandler.getTexture("canyonfruit.png")
        Material.TROPICALFRUIT -> DefaultTextureHandler.getTexture("Tropicalfruit.png")
    }
}
fun getKeyItemTextures(keyItem: KeyItem): Texture{
    return when(keyItem){
        KeyItem.FROZENHEART -> DefaultTextureHandler.getTexture("frozen-heart.png")
        KeyItem.FIREHEART -> DefaultTextureHandler.getTexture("fire-heart.png")
        KeyItem.MAP -> DefaultTextureHandler.getTexture("book.png")
        KeyItem.CROWN -> DefaultTextureHandler.getTexture("crown.png")
    }
}

class Dud(gameObjectData: GameObjectData):GameObject(gameObjectData){
    override val layer = Layer.ONGROUND

    override fun render(batch: SpriteBatch) {
    }

}

fun createItem(gameObjectData: GameObjectData): GameObject {
    val customFields = Json.decodeFromJsonElement<ItemCustomFields>(gameObjectData.customFields)
    if(customFields.KeyItem){
        return KeyItemObject(gameObjectData)
    } else {
        if(RandomManager.roll(customFields.PercentChance)){
            val itemText = customFields.Item
            val material = Material.values().find { it.name == itemText }
            return MaterialItem(gameObjectData, material!!)
        } else {
            return Dud(gameObjectData)
        }
    }
}

class MaterialItem(gameObjectData: GameObjectData, var material: Material) : WorldItem(gameObjectData) {

    override val itemAquiredText = "You found a ${material!!.name}!"
    override val texture = getMaterialTexture(material!!)

    override fun itemGained() {
        val currentValue =  generalSaveState.inventory.materialItems[material]
        generalSaveState.inventory.materialItems[material] = if(currentValue == null ) 1 else currentValue + 1
        generalSaveState.updateSaveState()
        this.remove()

        if(material !in player.materialsPickedUp){
            player.materialsPickedUp.add(material)
        }
        anivolutionCheck()
    }

    override fun render(batch: SpriteBatch) {
        super.render(batch)
    }
}

@Serializable
data class ItemCustomFields(val Item: String, val PercentChance: Int, val KeyItem: Boolean){

}