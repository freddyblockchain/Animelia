package com.mygdx.game.BaseClasses

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Enums.Layer

interface Renderable {
    val layer: Layer
    fun render(batch: SpriteBatch)
}