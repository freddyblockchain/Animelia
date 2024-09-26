package com.mygdx.game.Animation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Managers.AreaManager
import com.mygdx.game.Utils.Center
import com.mygdx.game.mainCamera
import kotlin.math.min

class AreaTransitionAnimation: Animation {
    override val durationFrames = 150
    override val animationAction = {}
    override var currentFrame = 0
    override var actionFrame = 0

    var offsetY = 0f

    val blackBox = Sprite(DefaultTextureHandler.getTexture("AreaBox.png"))

    val blackBoxTexture = DefaultTextureHandler.getTexture("AreaBox.png")

    val increment = 2
    override val layer = Layer.FOREGROUND

    var currentAlpha = 0f

    var alphaIncrement = 1f/90f

    val textFont = FontManager.TextFont

    override fun reset() {

    }
    override fun render(batch: SpriteBatch) {
        val screenCoordinates = Vector3((Center.x), 0f, 0f)

        // Unproject the screen coordinates to get the world coordinates
        val worldCoordinates = mainCamera.unproject(screenCoordinates)

        blackBox.setPosition(worldCoordinates.x, worldCoordinates.y)

        batch.draw(blackBoxTexture, blackBox.x, blackBox.y - offsetY, blackBox.width, offsetY,0,0,blackBox.width.toInt(),offsetY.toInt(),false,false)

        if(currentFrame < 33){
            offsetY += 1
        }

        else if (currentFrame == 118){
            //only do once
            currentAlpha = 1f
            alphaIncrement = -(1/32f)
        }
        else if (currentFrame > 118){
            offsetY -= 1
        }

        val textPosition = Vector2(blackBox.x + 20f, blackBox.y - 8f)
        textFont.color = Color(1f, 1f, 1f, min(currentAlpha,1f))  // RGB set to white (1f, 1f, 1f) and alpha to 0.5f
        textFont.draw(batch, AreaManager.getActiveArea()!!.areaIdentifier, textPosition.x, textPosition.y)
        textFont.color = Color.WHITE

        currentAlpha += alphaIncrement

        //blackBox.draw(batch)

    }
}