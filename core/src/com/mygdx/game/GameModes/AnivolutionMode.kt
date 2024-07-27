package com.mygdx.game.GameModes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Animelia.ANIMELIA_ENTITY
import com.mygdx.game.Animelia.getAnimeliaData
import com.mygdx.game.camera
import com.mygdx.game.currentGameMode
import com.mygdx.game.mainMode
import com.mygdx.game.player
import kotlin.math.min

class AnivolutionMode(val prevMode: GameMode, val animeliaEntity: ANIMELIA_ENTITY): GameMode {
    override val spriteBatch = SpriteBatch()
    override val inputProcessor = emptyInputProcessor
    val origZoom = camera.zoom

    var currentFrame = 0
    val playerSprite =  player.sprite
    var yOffsetUp = 0
    var yoffsetDown = 0

    override fun FrameAction() {
        if(currentFrame <= 90){
            camera.zoom -= 0.005f
        }
        else if(currentFrame <= 150){
            playerSprite.setRegion(0, yOffsetUp,playerSprite.width.toInt(),playerSprite.height.toInt());
            yOffsetUp += 1
        }
        else if(currentFrame == 151){
            player.animeliaInfo = getAnimeliaData(animeliaEntity)
        }
        else if(currentFrame <= 210){
            playerSprite.setRegion(0, playerSprite.height.toInt() - (min(yoffsetDown, playerSprite.height.toInt())),playerSprite.width.toInt(),playerSprite.height.toInt());
            yoffsetDown += 1
        }
        else if(currentFrame <= 270){
            camera.zoom += 0.005f
        }
        spriteBatch.begin()
        player.render(spriteBatch)
        spriteBatch.end()
        currentFrame += 1

        if(currentFrame > 270){
            camera.zoom = origZoom
            changeMode(prevMode)
        }
    }
}