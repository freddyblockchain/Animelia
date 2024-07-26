package com.mygdx.game.Animation

import FontManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer

class TextAnimation(val color: Color, val text: String, val pos: Vector2, moveTextUp: Boolean = true, override val duration: Int = 60): DefaultAnimation() {
    override val layer = Layer.AIR
    val font = FontManager.TextFont

    var yIncrement = 0f

    override fun render(batch: SpriteBatch) {
        val beforeColor = font.color
        font.color = color
        font.draw(batch, text, pos.x, pos.y + yIncrement)
        font.color = beforeColor

        yIncrement += 0.5f
    }
}