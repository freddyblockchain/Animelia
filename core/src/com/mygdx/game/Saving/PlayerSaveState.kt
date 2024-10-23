package com.mygdx.game.Saving

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.FileHandler
import com.mygdx.game.Inventory.Inventory
import com.mygdx.game.Managers.Stats
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//Too lazy to make serializer for vector2
@Serializable
class SVector2(var x: Float, var y: Float)

@Serializable
class PlayerSaveState(
    var inventory: Inventory,
    var stats: Stats,
    var currentAnimelia: ANIMELIA_ENTITY,
    var pos: SVector2,
    var areaIdentifier: String,
){
    fun updateSaveState(){
        val stringEncoded = Json.encodeToString(this)
        FileHandler.savePlayerState(stringEncoded)
    }
}