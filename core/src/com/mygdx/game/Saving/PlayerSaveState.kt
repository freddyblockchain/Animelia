package com.mygdx.game.Saving

import com.mygdx.game.FileHandler
import com.mygdx.game.Inventory.Inventory
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class PlayerSaveState(
    var inventory: Inventory
){
    fun updateSaveState(){
        val stringEncoded = Json.encodeToString(this)
        FileHandler.savePlayerState(stringEncoded)
    }
}