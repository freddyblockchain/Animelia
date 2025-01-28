package com.mygdx.game.Items

import com.mygdx.game.Animelia.Egg
import com.mygdx.game.Animelia.getEggTexture
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.generalSaveState
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class EggItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {
    val itemText = Json.decodeFromJsonElement<EggItemCustomFields>(gameObjectData.customFields).Egg
    val egg = getEggs()
    override val itemAquiredText = "You found a ${itemText} Egg!"
    override val texture = DefaultTextureHandler.getTexture(getEggTexture(egg))

    override fun itemGained() {
        super.itemGained()
        generalSaveState.inventory.eggs.add(egg)
        generalSaveState.updateSaveState()
    }

    fun getEggs(): Egg{
        return when(itemText){
            "Ice" -> Egg.ICE
            "Sound" -> Egg.SOUND
            "Flying" -> Egg.FLYING
            else -> Egg.FIRE
        }
    }

}

@Serializable
data class  EggItemCustomFields(val Egg: String){

}