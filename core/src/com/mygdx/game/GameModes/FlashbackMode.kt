package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Animation.Conversation
import com.mygdx.game.Enums.Direction
import com.mygdx.game.Enums.getDirectionUnitVector
import com.mygdx.game.Managers.AnimationManager
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.changeArea
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode
import com.mygdx.game.player

class FlashbackMode(val prevPosition: Vector2, val prevAreaIdentifier: String): GameMode {
    var frameCounter = 0
    var playerMoveForward: Boolean = false
    override val spriteBatch = SpriteBatch()

    override fun FrameAction() {
        for(gameObject in AreaManager.getActiveArea()!!.gameObjects){
            gameObject.frameTask()
        }
        if(frameCounter == 0){
            changeArea(Vector2(128f,32f), "e73c6da0-d7b0-11ee-9742-ab29f4293810", false)
            AnimationManager.animationManager.add(Conversation("Flashback1") {
                playerMoveForward = true})
        }
        if(playerMoveForward){
            player.move(getDirectionUnitVector(Direction.UP))
        }
        if(frameCounter >= 900){
            changeArea(prevPosition, prevAreaIdentifier, false)
            currentGameMode = mainMode
        }
        frameCounter++
    }
}