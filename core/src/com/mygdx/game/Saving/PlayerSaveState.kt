package com.mygdx.game.Saving

import com.mygdx.game.FileHandler
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.SaveHandling.SaveableObject
import com.mygdx.game.player
import com.mygdx.game.playerSaveState
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class PlayerSaveState(var playerXPos: Float, var playerYPos: Float,
                      var areaIdentifier: String,override val entityId:Int):SaveableObject(){

    fun update(){
        playerXPos = player.sprite.x
        playerYPos = player.sprite.y
        areaIdentifier = AreaManager.getActiveArea()!!.areaIdentifier
    }
    fun encode(): String{
        return Json.encodeToString(this)
    }
}
fun updateAndSavePlayer(){
    playerSaveState.update()
    FileHandler.savePlayerState(playerSaveState.encode())
}