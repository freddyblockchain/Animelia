package com.mygdx.game.WorldItems

import com.mygdx.game.Animelia.Egg
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.GameObjectData
import com.mygdx.game.player
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class EggItem(gameObjectData: GameObjectData) : WorldItem(gameObjectData) {
    val itemText = Json.decodeFromJsonElement<EggItemCustomFields>(gameObjectData.customFields).Egg
    override val itemAquiredText = "You found a ${itemText} Egg!"
    override val texture = DefaultTextureHandler.getTexture(getEggTexture())

    fun getEggTexture(): String{
        return when(itemText){
            "Ice" -> "iceegg.png"
            else -> "fireegg.png"
        }
    }
    override fun itemGained() {
        super.itemGained()
        val egg = getEgg()
        player.eggs.add(egg)
    }

    fun getEgg(): Egg{
        return when(itemText){
            "Ice" -> Egg.ICE
            else -> Egg.FIRE
        }
    }

}

@Serializable
data class  EggItemCustomFields(val Egg: String){

}