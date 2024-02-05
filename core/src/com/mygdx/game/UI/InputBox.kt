package com.mygdx.game.UI

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.BaseClasses.Clickable
import com.mygdx.game.BaseClasses.DefaultClickable
import com.mygdx.game.BaseClasses.Renderable
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer

class InputBox(position: Vector2, size: Vector2): DefaultClickable(position, size), Renderable{
    override val layer = Layer.AIR
    val boxtexture = DefaultTextureHandler.getTexture("inputbox.png")

    val boxsprite = Sprite(boxtexture)

    init {
        boxsprite.setSize(size.x, size.y)
        boxsprite.setOriginCenter()
        boxsprite.setPosition(boxsprite.x, boxsprite.y)
    }

    override fun render(batch: SpriteBatch) {
        boxsprite.draw(batch)
    }
}