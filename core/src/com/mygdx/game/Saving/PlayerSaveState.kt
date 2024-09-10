package com.mygdx.game.Saving

import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.FileHandler
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.Managers.Stats
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class PlayerSaveState(
    var inventory: Inventory,
    var stats: Stats,
    var currentAnimelia: ANIMELIA_ENTITY
){
    fun updateSaveState(){
        val stringEncoded = Json.encodeToString(this)
        FileHandler.savePlayerState(stringEncoded)
    }
}