package com.mygdx.game.UI.Conversation

import FontManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
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
    val texture = DefaultTextureHandler.getTexture("black-box.png")
    val sprite = Sprite(texture)
    val textFont = FontManager.TextFont

    init {
        sprite.setSize(Gdx.graphics.width.toFloat() / zoomX, 100f)
        val screenCoordinates = Vector3(0f, Gdx.graphics.height.toFloat(), 0f)

        // Unproject the screen coordinates to get the world coordinates
        val worldCoordinates = mainCamera.unproject(screenCoordinates)
        // Set the position of the sprite to the calculated world coordinates
        sprite.setPosition(worldCoordinates.x, worldCoordinates.y)
    }

    fun render(batch: SpriteBatch, textData: SpeechData) {
        sprite.draw(batch)
        val text = textData.text
        val lastPart = text.indexOf(" ", 51)
        val firstIndex = if(lastPart == -1) text.length else lastPart + 1
        textFont.draw(batch, textData.text.substring(0, firstIndex), sprite.x + 20f, sprite.y + 50f)
        if(firstIndex != -1){
            textFont.draw(batch, textData.text.substring(firstIndex, text.length), sprite.x + 20f, sprite.y + 30f)
        }
        textFont.color = Color.YELLOW
        textFont.draw(batch, textData.speaker, sprite.x + 20f, sprite.y + 80f)
        textFont.color = Color.WHITE
    }
}