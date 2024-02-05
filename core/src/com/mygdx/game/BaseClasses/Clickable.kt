package com.mygdx.game.BaseClasses

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.DefaultTextureHandler
import com.mygdx.game.Enums.Layer
import com.mygdx.game.unaryMinus

interface Clickable {
    var selected: Boolean
    fun onClicked()
    fun onFocusAway()
}

abstract class DefaultClickable(val position: Vector2, val size: Vector2): Renderable, Clickable{
    override val layer = Layer.AIR
    val texture = DefaultTextureHandler.getTexture("selectedborder.png")

    val sprite = Sprite(texture)

    override var selected = false

    init {
        sprite.setSize(size.x + 5f, size.y + 5f)
        sprite.setOriginCenter()
        sprite.setPosition(position.x - 5f, position.y - 5f)
    }

    override fun render(batch: SpriteBatch) {
        if(selected){
            sprite.draw(batch)
        }
    }

    override fun onClicked() {
        selected = true
    }

    override fun onFocusAway() {
        selected = false
    }
}