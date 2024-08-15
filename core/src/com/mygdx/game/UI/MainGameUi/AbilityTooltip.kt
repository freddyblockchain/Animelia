package com.mygdx.game.UI.MainGameUi

import FontManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Enums.Layer
import com.mygdx.game.Rendering.Renderable

class AbilityTooltip(val number: Int, val texture: Texture): Renderable {
    override val layer = Layer.FOREGROUND
    val sprite = Sprite(texture)
    val mediumFont = FontManager.MediumFont

    init {
        sprite.setSize(64f,64f)
    }

    override fun render(batch: SpriteBatch) {
        val pos = Vector2(sprite.x, sprite.y)
        sprite.draw(batch)
        mediumFont.draw(batch, number.toString(), pos.x + 32f, pos.y + 32f)
    }

}