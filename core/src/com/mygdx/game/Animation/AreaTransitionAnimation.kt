package com.mygdx.game.Animation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Utils.Center
import com.mygdx.game.mainCamera

class AreaTransitionAnimation: Animation {
    override val durationFrames = 300
    override val animationAction = {}
    override var currentFrame = 0
    override var actionFrame = 0

    var offsetY = 0f

    val blackBox = Sprite(DefaultTextureHandler.getTexture("AreaBox.png"))

    val blackBoxTexture = DefaultTextureHandler.getTexture("AreaBox.png")

    val increment = 2
    override val layer = Layer.FOREGROUND

    override fun reset() {

    }
    override fun render(batch: SpriteBatch) {
        val screenCoordinates = Vector3((Center.x), 0f, 0f)

        // Unproject the screen coordinates to get the world coordinates
        val worldCoordinates = mainCamera.unproject(screenCoordinates)

        blackBox.setPosition(worldCoordinates.x, worldCoordinates.y - blackBox.height)

        batch.draw(blackBoxTexture, blackBox.x, blackBox.y - offsetY, blackBox.width, offsetY,0,0,blackBox.width.toInt(),offsetY.toInt(),false,false)

        if(currentFrame < 33){
            offsetY += 1
        }

        else if (currentFrame > 268){
            offsetY -= 1
        }

        //blackBox.draw(batch)

    }
}