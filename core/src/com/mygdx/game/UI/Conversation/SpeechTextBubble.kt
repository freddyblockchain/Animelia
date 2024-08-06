package com.mygdx.game.UI.Conversation

import FontManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Rendering.Renderable
import com.mygdx.game.mainCamera
import com.mygdx.game.zoomX
import com.mygdx.game.zoomY

class SpeechTextBubble() {
    val texture = DefaultTextureHandler.getTexture("EmptyDoor.png")
    val sprite = Sprite(texture)
    val textFont = FontManager.TextFont

    init {
        sprite.setSize(Gdx.graphics.width.toFloat() / zoomX, 100f)
        val screenCoordinates = Vector3(0f, Gdx.graphics.height.toFloat(), 0f)

        // Unproject the screen coordinates to get the world coordinates
        val worldCoordinates = mainCamera.unproject(screenCoordinates)
        println(worldCoordinates)
        // Set the position of the sprite to the calculated world coordinates
        sprite.setPosition(worldCoordinates.x, worldCoordinates.y)
    }

    fun render(batch: SpriteBatch, textData: SpeechData) {
        sprite.draw(batch)
        textFont.draw(batch, textData.text, sprite.x + 20f, sprite.y + 50f)
    }
}